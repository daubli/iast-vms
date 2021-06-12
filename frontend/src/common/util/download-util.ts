import { AxiosResponse } from 'axios';
import { saveAs } from 'file-saver';

export default function downloadFromResponse(response: AxiosResponse, overrideName?: string) {
	let fileType: string;
	let fileName: string;

	if (response.headers['content-type']) {
		fileType = response.headers['content-type'];
	} else {
		throw new Error('content-type header is empty');
	}

	// parsing filename from response header, see https://stackoverflow.com/a/40940790
	const filenameRegex = /filename[^;=\n]*=((['"]).*?\2|[^;\n]*)/;
	const matches = filenameRegex.exec(response.headers['content-disposition']);

	if (!!overrideName && overrideName.length) {
		fileName = overrideName;
	} else if (matches != null && matches[1]) {
		fileName = matches[1].replace(/['"]/g, '');
	} else {
		throw new Error('file name is empty');
	}

	const blob = new Blob([response.data], {
		type: fileType
	});

	saveAs(blob, fileName);
}
