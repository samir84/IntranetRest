package com.selazzouzi.intranet.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.selazzouzi.intranet.model.Absence;

public interface AbsenceRepository extends JpaRepository<Absence, Long> {

}
