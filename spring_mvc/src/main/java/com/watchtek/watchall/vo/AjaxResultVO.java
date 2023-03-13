package com.watchtek.watchall.vo;

/**
 * Ajax 요청에 대한 결과 반환용 VO <br>
 * Ajax 요청에 대한 결과를 반환할 때 사용하는 VO.
 *
 * @author Ju
 * @version 1.0
 */
public class AjaxResultVO
{

    /**
     * 서버 로직에 대한 성공 여부 설정
     */
    private Boolean success;
    /**
     * 페이징 처리를 위한 Total Count
     */
    private Integer total;
    /**
     * Ajax 요청에 대한 결과 Data
     */
    private Object data;
    /**
     * Ajax 요청에 대한 결과 Message
     */
    private String message;
    
    private Object data2;
    
    public Object getData2()
    {
        return data2;
    }

    public void setData2(Object data2)
    {
        this.data2 = data2;
    }
    

    public AjaxResultVO(boolean success, Object data)
    {
        this.setSuccess(success);
        this.setData(data);
    }

    public Boolean getSuccess()
    {
        return success;
    }

    public void setSuccess(Boolean success)
    {
        this.success = success;
    }
    
    public void setTotal(int total) 
    {
    	this.total = total;
    }

    public Object getData()
    {
        return data;
    }

    public void setData(Object data)
    {
        this.data = data;
    }

    public String getMessage()
    {
        return message;
    }

    public void setMessage(String message)
    {
        this.message = message;
    }

    public Integer getTotal()
    {
        return total;
    }

}