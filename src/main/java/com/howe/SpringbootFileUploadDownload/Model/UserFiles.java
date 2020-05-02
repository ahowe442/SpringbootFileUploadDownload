package com.howe.SpringbootFileUploadDownload.Model;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "user_files")
public class UserFiles implements Serializable {


    /****
     *
     */
    private static final long serialVersionUID = 1539939610542578926L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;
    @Column(name = "file_name")
    private String filename;
    @Column(name = "modified_file_name")
    private String modifiedFileName;
    @Column(name = "file_extension")
    private String fileExtension;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public String getModifiedFileName() {
        return modifiedFileName;
    }

    public void setModifiedFileName(String modifiedFileName) {
        this.modifiedFileName = modifiedFileName;
    }

    public String getFileExtension() {
        return fileExtension;
    }

    public void setFileExtension(String fileExtension) {
        this.fileExtension = fileExtension;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
