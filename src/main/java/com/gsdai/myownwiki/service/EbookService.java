package com.gsdai.myownwiki.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.gsdai.myownwiki.domain.Ebook;
import com.gsdai.myownwiki.domain.EbookExample;
import com.gsdai.myownwiki.mapper.EbookMapper;
import com.gsdai.myownwiki.req.EbookQueryReq;
import com.gsdai.myownwiki.req.EbookSaveReq;
import com.gsdai.myownwiki.resp.EbookQueryResp;
import com.gsdai.myownwiki.resp.PageResp;
import com.gsdai.myownwiki.util.CopyUtil;
import com.gsdai.myownwiki.util.SnowFlake;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import javax.annotation.Resource;
import java.util.List;

@Service
public class EbookService {

    @Resource
    private EbookMapper ebookMapper;

    @Resource
    private SnowFlake snowFlake;

    public PageResp<EbookQueryResp> list(EbookQueryReq req) {
        EbookExample ebookExample = new EbookExample();
        EbookExample.Criteria ebookExampleCriteria = ebookExample.createCriteria();
        if (!ObjectUtils.isEmpty(req.getName())) {
            ebookExampleCriteria.andNameLike("%" + req.getName() + "%");
        }
        if (!ObjectUtils.isEmpty(req.getCategoryId2())) {
            ebookExampleCriteria.andCategory2IdEqualTo(req.getCategoryId2());
        }

        PageHelper.startPage(req.getPage(), req.getSize());
        List<Ebook> ebookList = ebookMapper.selectByExample(ebookExample);
        PageInfo<Ebook> pageInfo = new PageInfo<>(ebookList);

        List<EbookQueryResp> list = CopyUtil.copyList(ebookList, EbookQueryResp.class);
        PageResp<EbookQueryResp> pageResp = new PageResp<>();
        pageResp.setTotal(pageInfo.getTotal());
        pageResp.setList(list);
        return pageResp;
    }

    public void save(EbookSaveReq req) {
        Ebook ebook = CopyUtil.copy(req, Ebook.class);
        if (ObjectUtils.isEmpty(req.getId())) {
            ebook.setId(snowFlake.nextId());
            ebookMapper.insert(ebook);
        } else {
            ebookMapper.updateByPrimaryKey(ebook);
        }
    }

    public void delete(Long id) {
        ebookMapper.deleteByPrimaryKey(id);
    }
}
