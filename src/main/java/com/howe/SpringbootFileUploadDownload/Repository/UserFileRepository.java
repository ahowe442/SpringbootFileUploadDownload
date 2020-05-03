package com.howe.SpringbootFileUploadDownload.Repository;

import com.howe.SpringbootFileUploadDownload.Model.UserFiles;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository

public interface UserFileRepository extends CrudRepository<UserFiles, Long> {

    @Query("select f from UserFiles as f where f.user.id = ?1")
    List<UserFiles> findFilesByUserId(Long userId);
}
