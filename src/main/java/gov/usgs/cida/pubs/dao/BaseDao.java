package gov.usgs.cida.pubs.dao;


import gov.usgs.cida.pubs.aop.ISetDbContext;
import gov.usgs.cida.pubs.dao.intfc.IDao;

import java.util.List;
import java.util.Map;

import org.mybatis.spring.support.SqlSessionDaoSupport;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author drsteini
 * @param <D> the specific domain of the object 
 *
 */
public abstract class BaseDao<D> extends SqlSessionDaoSupport implements IDao<D> {

    public static final String ADD = ".add";
    public static final String DELETE = ".delete";
    public static final String DELETE_BY_PARENT = ".deleteByParent";
    public static final String GET_BY_ID = ".getById";
    public static final String GET_BY_MAP = ".getByMap";
    public static final String UPDATE = ".update";
    public static final String GET_COUNT = ".getCount";

    /**
     * {@inheritDoc}
     * 
     * @see gov.usgs.cida.pubs.dao.intfc.IDao#getClientId()
     */
    @Override
    @Transactional(readOnly = true)
    @ISetDbContext
    public String getClientId() {
        return (String) getSqlSession().selectOne("getClientId");
    }

    /** {@inheritDoc}
     * @see gov.usgs.cida.pubs.dao.intfc.IDao#add(java.lang.Object)
     */
    @Transactional(readOnly = true)
    @Override
    public Integer add(D domainObject) {
        throw new RuntimeException("NOT IMPLEMENTED.");
    }

    /** {@inheritDoc}
     * @see gov.usgs.cida.pubs.dao.intfc.IDao#getById(java.lang.Integer)
     */
    @Transactional(readOnly = true)
    @Override
    public D getById(Integer domainID) {
        throw new RuntimeException("NOT IMPLEMENTED.");
    }

    /** {@inheritDoc}
     * @see gov.usgs.cida.pubs.dao.intfc.IDao#getById(java.lang.Integer)
     */
    @Transactional(readOnly = true)
    @Override
    public D getById(String domainID) {
        throw new RuntimeException("NOT IMPLEMENTED.");
    }

    /** {@inheritDoc}
     * @see gov.usgs.cida.pubs.dao.intfc.IDao#getById(java.lang.Integer)
     */
    @Transactional(readOnly = true)
    @Override
    public final boolean exists(Integer domainID) {
        return null!=getById(domainID);
    }

    /** {@inheritDoc}
     * @see gov.usgs.cida.pubs.dao.intfc.IDao#getById(java.lang.Integer)
     */
    @Transactional(readOnly = true)
    @Override
    public final boolean exists(String domainID) {
        return null!=getById(domainID);
    }

    /** 
     * {@inheritDoc}
     * @see gov.usgs.cida.pubs.dao.intfc.IDao#getByMap(Map)
     */
    @Transactional(readOnly = true)
    @Override
    public List<D> getByMap(Map<String, Object> filters) {
        throw new RuntimeException("NOT IMPLEMENTED.");
    }

    /** 
     * {@inheritDoc}
     * @see gov.usgs.cida.pubs.dao.intfc.IDao#getObjectCount(Map)
     */
    @Transactional(readOnly = true)
    @Override
    public Integer getObjectCount(Map<String, Object> filters) {
        throw new RuntimeException("NOT IMPLEMENTED.");
    }

    /** {@inheritDoc}
     * @see gov.usgs.cida.pubs.dao.intfc.IDao#update(java.lang.Object)
     */
    @Transactional(readOnly = true)
    @Override
    public void update(D domainObject) {
        throw new RuntimeException("NOT IMPLEMENTED.");
    }

    /** {@inheritDoc}
     * @see gov.usgs.cida.pubs.dao.intfc.IDao#delete(java.lang.Object)
     */
    @Transactional(readOnly = true)
    @Override
    public void delete(D domainObject) {
        throw new RuntimeException("NOT IMPLEMENTED.");
    }

    /** {@inheritDoc}
     * @see gov.usgs.cida.pubs.dao.intfc.IDao#deleteById(java.lang.Integer)
     */
    @Transactional(readOnly = true)
    @Override
    public void deleteById(Integer domainID) {
        throw new RuntimeException("NOT IMPLEMENTED.");
    }

    /** 
     * {@inheritDoc}
     * @see gov.usgs.cida.pubs.dao.intfc.IDao#deleteByParent(java.lang.Integer)
     */
    @Transactional(readOnly = true)
    @Override
    public void deleteByParent(Integer parentID) {
        throw new RuntimeException("NOT IMPLEMENTED.");
    }

}