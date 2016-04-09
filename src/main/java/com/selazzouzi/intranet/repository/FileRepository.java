package com.selazzouzi.intranet.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.selazzouzi.intranet.model.File;

public interface FileRepository extends JpaRepository<File, Long> {

}
