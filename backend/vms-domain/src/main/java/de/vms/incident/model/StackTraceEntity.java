package de.vms.incident.model;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import javax.persistence.*;

@Entity
public class StackTraceEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private UUID id;

    @Column(columnDefinition="TEXT", length=10485760)
    @ElementCollection
    private List<String> stackOfCalls;

    @Access(AccessType.PROPERTY)
    @Lob
    private String content;

    public StackTraceEntity() {
    }

    public StackTraceEntity(String content, List<String> stackOfCalls) {
        this.stackOfCalls = stackOfCalls;
        this.content = content;
    }

    public StackTraceEntity(List<String> stackOfCalls) {
        this.stackOfCalls = stackOfCalls;
        this.setContent(stackOfCalls);
    }

    public void setStackOfCalls(List<String> stackOfCalls) {
        this.stackOfCalls = stackOfCalls;
    }

    public List<String> getStackOfCalls() {
        return this.stackOfCalls;
    }

    private void setContent(String content) {
        this.content = content;
    }

    private void setContent(List<String> stackOfCalls) {
        this.content = stackOfCalls.stream().collect(Collectors.joining("\n"));
    }

    public String getContent() {
        return content;
    }


    @Override
    public boolean equals(Object stacktrace) {
        return stacktrace instanceof StackTraceEntity && (stacktrace == this || this.getContent()
                .equals(((StackTraceEntity) stacktrace).getContent()));
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }
}
