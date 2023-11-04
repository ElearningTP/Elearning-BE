package com.api.learning.ElearningBE.storage.id;

import com.api.learning.ElearningBE.storage.base.Auditable;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.id.IdentifierGenerator;

import java.io.Serializable;

@Slf4j
public class IdGenerator implements IdentifierGenerator {

    @Override
    public Serializable generate(SharedSessionContractImplementor sharedSessionContractImplementor, Object o) throws HibernateException {
        try{
            Auditable reuseId = (Auditable) o;
            if(reuseId.getId()!=null){
                return reuseId.getId();
            }
        }catch (Exception e){
            log.error("Occurred error when generator id: "+ e.getMessage());
        }
        return SnowFlakeIdService.getInstance().nextId();
    }
}
