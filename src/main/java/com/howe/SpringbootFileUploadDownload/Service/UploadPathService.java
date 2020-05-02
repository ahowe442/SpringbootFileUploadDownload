package com.howe.SpringbootFileUploadDownload.Service;

import java.io.File;

public interface UploadPathService {
    File getFilePath(String modifiedFileName, String path);
}
