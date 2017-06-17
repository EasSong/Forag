package cuit.service;

import java.util.ArrayList;

/**
 * Created by Esong on 2017/6/17.
 */
public interface TagService {
    ArrayList<String> getTagListByLikeName(String likeName);
    ArrayList<String> getMsgIdListByLikeName(String likeName);
}
