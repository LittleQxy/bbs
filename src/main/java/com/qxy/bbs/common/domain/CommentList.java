package com.qxy.bbs.common.domain;

import com.qxy.bbs.domain.dto.CommentDto;
import com.qxy.bbs.domain.dto.ReplyDto;
import lombok.Data;

import java.util.List;

/**
 * @author qixiangyang5
 * @create 2020/7/27 16:48
 */
@Data
public class CommentList {

    private CommentDto comment;//主评论

    private List<ReplyDto> list;//评论下面的回复

    private int total;

}
