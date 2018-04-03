package com.xiaoyu.lingdian.vo;

/**
 * 试卷题目
 *
 * @author: zhangy
 * @since: 2017年12月31日 14:44:11
 * @history:
 */
public class QuestionsVO {

    /**
     * 题目名称
     */
    private String name;

    /**
     * 视频目录
     */
    private String dirMovie;

    /**
     * 回答内容（特定格式文字编辑）
     */
    private String content;

    /**
     * 答案（特定格式文字编辑）
     */
    private String result;

    /**
     * 对错（1对2错）
     */
    private Integer judge;

    /**
     * 得分
     */
    private Integer score;

    /**
     * 分值
     */
    private Integer oldScore;

    /**
     * 视频名称
     */
    private String movie;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDirMovie() {
        return dirMovie;
    }

    public void setDirMovie(String dirMovie) {
        this.dirMovie = dirMovie;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public Integer getJudge() {
        return judge;
    }

    public void setJudge(Integer judge) {
        this.judge = judge;
    }

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }

    public Integer getOldScore() {
        return oldScore;
    }

    public void setOldScore(Integer oldScore) {
        this.oldScore = oldScore;
    }

    public String getMovie() {
        return movie;
    }

    public void setMovie(String movie) {
        this.movie = movie;
    }

    public QuestionsVO() {
    }

}