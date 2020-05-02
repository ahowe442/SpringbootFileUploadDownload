package com.howe.SpringbootFileUploadDownload.Repository;

import com.howe.SpringbootFileUploadDownload.Model.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {

}
