package com.yipin.project.rpt;

import com.yipin.project.domain.secret.SecretKey;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SecretKeyRpt extends JpaRepository<SecretKey,Long> {
}
