package de.vms.javaagent.sensor.tainttracker.transformer;

import static net.bytebuddy.matcher.ElementMatchers.*;

import java.util.List;

import net.bytebuddy.matcher.ElementMatcher;
import net.bytebuddy.matcher.ElementMatchers;

import de.vms.javaagent.sensor.tainttracker.config.MethodArgumentsConfig;

public class JunctionBuilder {
    private ElementMatcher.Junction junction;

    private boolean firstConditionIsSet;

    public JunctionBuilder() {
        this.junction = ElementMatchers.none();
        this.firstConditionIsSet = false;
    }

    public final ElementMatcher.Junction build() {
        return junction;
    }

    public final JunctionBuilder withOrJunction(final ElementMatcher.Junction orJunction) {
        this.junction = junction.or(orJunction);
        return this;
    }

    public final JunctionBuilder withMethodName(final String methodName) {
        if (!methodName.isEmpty()) {
            if (!firstConditionIsSet) {
                this.junction = junction.or(named(methodName));
                this.firstConditionIsSet = true;
            } else {
                this.junction = junction.and(named(methodName));
            }
        }
        return this;
    }

    public final JunctionBuilder withMethodArguments(final List<MethodArgumentsConfig> arguments) {
        if (!arguments.isEmpty()) {
            ElementMatcher.Junction innerJunction = null;
            for (int i = 0; i < arguments.size() - 1; i++) {
                innerJunction = junction.and(takesArgument(i, named(arguments.get(i).getType())));
            }

            if (innerJunction == null) {
                return this;
            }

            if (!firstConditionIsSet) {
                junction = junction.or(innerJunction);
                this.firstConditionIsSet = true;
            } else {
                junction = junction.and(innerJunction);
            }
        }
        return this;
    }

    public final JunctionBuilder withAnnotation(final String byMethodAnnotation) {
        if (!byMethodAnnotation.isEmpty()) {
            if (!firstConditionIsSet) {
                junction = junction.or(isAnnotatedWith(named(byMethodAnnotation)));
                this.firstConditionIsSet = true;
            } else {
                junction = junction.and(isAnnotatedWith(named(byMethodAnnotation)));
            }
        }
        return this;
    }

    public final JunctionBuilder withClassName(final String byClass) {
        if (!byClass.isEmpty()) {
            ElementMatcher.Junction innerJunction;
            if (byClass.startsWith("*")) {
                innerJunction = ElementMatchers.nameEndsWith(byClass.substring(1));
            } else if (byClass.endsWith("*")) {
                innerJunction = ElementMatchers.nameStartsWith(byClass.substring(0, byClass.length() - 1));
            } else if (byClass.equals("*")) {
                innerJunction = ElementMatchers.any();
            } else {
                innerJunction = ElementMatchers.named(byClass);
            }

            if (!firstConditionIsSet) {
                junction = junction.or(innerJunction);
                firstConditionIsSet = true;
            } else {
                junction = junction.and(innerJunction);
            }
        }
        return this;
    }

    public final JunctionBuilder withSuperTypeName(final String superTypeName) {
        if (!superTypeName.isEmpty()) {
            ElementMatcher.Junction innerJunction;
            if (superTypeName.startsWith("*")) {
                innerJunction = ElementMatchers.hasSuperType(nameEndsWith(superTypeName.substring(1)));
            } else if (superTypeName.endsWith("*")) {
                innerJunction = ElementMatchers.hasSuperClass(
                        nameStartsWith(superTypeName.substring(0, superTypeName.length() - 1)));
            } else {
                innerJunction = ElementMatchers.hasSuperType(named(superTypeName));
            }

            if (!firstConditionIsSet) {
                junction = junction.or(innerJunction);
                firstConditionIsSet = true;
            } else {
                junction = junction.and(innerJunction);
            }
        }
        return this;
    }

    public final JunctionBuilder withReturnType(final String returnTypeName) {
        if (!returnTypeName.isEmpty()) {
            if (!firstConditionIsSet) {
                junction = junction.or(returns(named(returnTypeName)));
                this.firstConditionIsSet = true;
            } else {
                junction = junction.and(returns(named(returnTypeName)));
            }
        }
        return this;
    }
}
