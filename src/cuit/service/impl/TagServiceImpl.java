package cuit.service.impl;

import cuit.dao.TagDao;
import cuit.service.TagService;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Esong on 2017/6/17.
 */
public class TagServiceImpl implements TagService {
    private TagDao tagDao;

    public void setTagDao(TagDao tagDao) {
        this.tagDao = tagDao;
    }

    @Override
    public ArrayList<String> getTagListByLikeName(String likeName) {
        List tagList = tagDao.getTagListByLikeName(likeName);
        ArrayList<String> tagArr = new ArrayList<>();
        for (Object obj:tagList){
            tagArr.add(String.valueOf(obj));
        }
        return tagArr;
    }

    @Override
    public ArrayList<String> getMsgIdListByLikeName(String likeName) {
        return null;
    }
}
