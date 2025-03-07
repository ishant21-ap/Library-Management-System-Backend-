package com.project.Library_management_system.repository;

import com.project.Library_management_system.model.Txn;
import com.project.Library_management_system.model.TxnStatus;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TxnRepository extends JpaRepository<Txn, Integer> {

    Txn findByUserPhoneNoAndBookBookNoAndTxnStatus(String phoneNo, String bookNo, TxnStatus txnStatus);
}
