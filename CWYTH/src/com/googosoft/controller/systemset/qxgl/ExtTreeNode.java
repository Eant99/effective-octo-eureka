package com.googosoft.controller.systemset.qxgl;

import java.util.ArrayList;
import java.util.List;

import com.googosoft.util.Validate;

/**
 * 
 * @author 崔屹东
 * @version 2016-01-05
 *
 */
public class ExtTreeNode {
	/**
	 * 节点的ID
	 */
    private String id = "";
    public void setId(String id)
    {
          this.id=id;
    }
    public String getId()
    {
    	return id;
    }
    /**
     * 节点显示的内容
     */
    private String text = "";
    
    public void setText(String text)
    {
          this.text=text;
    }
    public String getText()
    {
    	return text;
    }
    /**
     * 该节点是否是叶子节点 
     */
    private Boolean isLeaf = false;
    public void setIsleaf(Boolean isLeaf)
    {
          this.isLeaf=isLeaf;
    }
    public Boolean getIsleaf()
    {
    	return isLeaf;
    }
    /**
     * 设置该节点样式
     */
    private String cls = "";
    public void setCls(String cls)
    {
          this.cls=cls;
    }
    public String getCls()
    {
    	return cls;
    }

    /**
     * 是否打开单击展开事件
     */
    private Boolean ifSingleClickExpand = true;
    public void setIfSingleClickExpand(Boolean ifSingleClickExpand)
    {
          this.ifSingleClickExpand=ifSingleClickExpand;
    }
    public Boolean getIfSingleClickExpand()
    {
    	return ifSingleClickExpand;
    }

    /**
     * 该节点连接的地址
     */
    private String href = "";
    public void setHref(String href)
    {
          this.href=href;
    }
    public String getHref()
    {
    	return href;
    }
    /**
     * 节点的图标
     */
    private String icon ="";
    public void setIcon(String icon){
    	this.icon=icon;
    }
    public String getIcon(){
    	 return icon;
    	
    }

    /**
     * 节点连接指向的框架
     */
    private String hrefTarget = "";
    public void setHrefTarget(String hrefTarget)
    {
          this.hrefTarget=hrefTarget;
    }
    public String getHrefTarget()
    {
    	return hrefTarget;
    }
    /**
     * 是否展开该节点
     */
    private Boolean expanded = false;
    public void setExpanded(Boolean expanded)
    {
          this.expanded=expanded;
    }
    public Boolean getExpanded()
    {
    	return expanded;
    }

    /**
     * 是否展示checkbox
     */
    private Boolean extchecked = false;
    public void setExtchecked(Boolean extchecked)
    {
          this.extchecked=extchecked;
    }
    public Boolean getExtchecked()
    {
    	return extchecked;
    }

	/**
	 * 子节点
	 */
    private List<ExtTreeNode> children = new ArrayList<ExtTreeNode>();
    public void setChildren(List<ExtTreeNode> children)
    {
          this.children=children;
    }
    public List<ExtTreeNode> getChildren()
    {
    	return children;
    }


    public ExtTreeNode()
    {
    }

    public ExtTreeNode(String id)
    {
        this.id = id;
    }
  

    public ExtTreeNode(String id, String text, Boolean isleaf, Boolean ifSingleClickExpand, Boolean expanded)
    {
        this.id = id;
        this.text = text;
        this.isLeaf = isleaf;
        this.expanded = expanded;
        this.ifSingleClickExpand = ifSingleClickExpand;
    }
    public ExtTreeNode(String id, String text, Boolean isleaf, Boolean ifSingleClickExpand, Boolean expanded,String icon)
    {
    	this.id = id;
    	this.text = text;
    	this.isLeaf = isleaf;
    	this.expanded = expanded;
    	this.ifSingleClickExpand = ifSingleClickExpand;
    	this.icon=icon;
    }

    public ExtTreeNode(String id, String text, Boolean isleaf, Boolean ifSingleClickExpand, Boolean expanded, List<ExtTreeNode> children)
    {
        this.id = id;
        this.text = text;
        this.isLeaf = isleaf;
        this.ifSingleClickExpand = ifSingleClickExpand;
        this.expanded = expanded;
        this.children = children;
    }
    public ExtTreeNode(String id, String text, Boolean isleaf, Boolean ifSingleClickExpand, String href, String hrefTarget)
    {
        this.id = id;
        this.text = text;
        this.isLeaf = isleaf;
        this.ifSingleClickExpand = ifSingleClickExpand;
        this.href = href;
        this.hrefTarget = hrefTarget;
    }

    public ExtTreeNode(String id, String text, Boolean isleaf, Boolean ifSingleClickExpand, String href, String hrefTarget,String icon)
    {
        this.id = id;
        this.text = text;
        this.isLeaf = isleaf;
        this.ifSingleClickExpand = ifSingleClickExpand;
        this.href = href;
        this.hrefTarget = hrefTarget;
        this.icon=icon;
    }
    

    public ExtTreeNode(String id, String text, Boolean isleaf, Boolean ifSingleClickExpand, Boolean expanded, String href, String hrefTarget)
    {
        this.id = id;
        this.text = text;
        this.isLeaf = isleaf;
        this.ifSingleClickExpand = ifSingleClickExpand;
        this.expanded = expanded;
        if(Validate.noNull(href)){
        	this.href = href;
        }
        if(Validate.noNull(hrefTarget)){
        	this.hrefTarget = hrefTarget;
        }
    }
   
    /**
     * *11111111
     * @param id111111
     * @param textddd
     * @param isleaf 叶子节点
     * @param ifSingleClickExpand
     * @param expanded 是否展开
     * @param href
     * @param hrefTarget
     * @param cls
     */
    public ExtTreeNode(String id, String text, Boolean isleaf, Boolean ifSingleClickExpand, Boolean expanded, String href, String hrefTarget, String icon)
    {
    	
        this.id = id;
        this.text = text;
        this.icon = icon;
        this.isLeaf = isleaf;
        this.ifSingleClickExpand = ifSingleClickExpand;
        this.expanded = expanded;
        if(Validate.noNull(href)){
        	this.href = href;
        }
        if(Validate.noNull(hrefTarget)){
        	this.hrefTarget = hrefTarget;
        }
    }
    public ExtTreeNode(String id, String text, Boolean isleaf, Boolean ifSingleClickExpand, String href, String hrefTarget, Boolean chkbox)
    {
        this.id = id;
        this.text = text;
        this.isLeaf = isleaf;
        this.ifSingleClickExpand = ifSingleClickExpand;
        if(Validate.noNull(href)){
        	this.href = href;
        }
        if(Validate.noNull(hrefTarget)){
        	this.hrefTarget = hrefTarget;
        }
        this.extchecked = chkbox;
    }

    /// 获取该节点的json不等式
    public String ToJsonString()
    {
        StringBuilder json = new StringBuilder();

        json.append("{");

        json.append("id:\"" + id + "\"");

        json.append(",text:\"" + (text.length() > 0 ? text : id) + "\"");

        if (cls.length() > 0)
        {
            json.append(",cls:\"" + cls + "\"");
        }
        if (icon.length() > 0)
        {
        	json.append(",icon:\"" + icon + "\"");
        }
        json.append(",leaf:" + isLeaf.toString().toLowerCase());

        json.append(",singleClickExpand:" + ifSingleClickExpand.toString().toLowerCase());

        json.append(",expanded:" + expanded.toString().toLowerCase());
        if (extchecked == true)
        {
            json.append(",checked:false");
        }

        if (href.length() > 0)
        {
            json.append(",href:\"" + href + "\"");
        }

        if (hrefTarget.length() > 0)
        {
            json.append(",hrefTarget:\"" + hrefTarget + "\"");
        }

        if (children.size() > 0)
        {
            json.append(",children:[");
            for(ExtTreeNode node:children)
            {
                json.append(node.ToJsonString() + ",");
            }
            json = json.deleteCharAt(json.length() - 1);
            json.append("]");
        }

        json.append("}");

        return json.toString();
    }

    /// 获取该节点的 子节点的 json不等式
    public String GetChildrenJsonString()
    {
        StringBuilder json = new StringBuilder();

        json.append("[");
        for(ExtTreeNode node : children)
        {
            json.append(node.ToJsonString() + ",");
        }
        json = json.deleteCharAt(json.length() - 1);
        json.append("]");

        return json.toString();
    }
}
