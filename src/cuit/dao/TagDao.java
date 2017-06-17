package cuit.dao;

import java.util.List;

/**
 * Created by Esong on 2017/6/17.
 */
public interface TagDao {
    List getTagListByLikeName(String likeName);
    List getMsgIdListByLikeName(String likeName);
}
