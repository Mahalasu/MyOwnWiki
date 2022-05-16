package com.gsdai.myownwiki.controller;

import com.gsdai.myownwiki.req.DocSaveReq;
import com.gsdai.myownwiki.resp.DocQueryResp;
import com.gsdai.myownwiki.resp.CommonResp;
import com.gsdai.myownwiki.service.DocService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/doc")
public class DocController {
    @Resource
    private DocService docService;

    @GetMapping("/all")
    public CommonResp all() {
        CommonResp<List<DocQueryResp>> commonResp = new CommonResp<>();
        List<DocQueryResp> list = docService.all();
        commonResp.setContent(list);
        return commonResp;
    }

    @PostMapping("/save")
    public CommonResp save(@Valid @RequestBody DocSaveReq req) {
        CommonResp commonResp = new CommonResp<>();
        docService.save(req);
        return commonResp;
    }

    @DeleteMapping("/delete/{id}")
    public CommonResp delete(@PathVariable Long id) {
        CommonResp commonResp = new CommonResp<>();
        docService.delete(id);
        return commonResp;
    }
}