package com.howe.SpringbootFileUploadDownload.Repository;

import com.howe.SpringbootFileUploadDownload.Model.UserFiles;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository

public interface UserFileRepository extends CrudRepository<UserFiles, Long> {

}
