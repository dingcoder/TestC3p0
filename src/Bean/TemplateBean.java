package Bean;

/**
*
*Description:
*@author Karoy
*Start time:2018年9月28日
*Version:v0.1 2018年9月28日
*
*/

public class TemplateBean {
	private String template_id;
	private String template_name;
	private String template_content;
	private int param_number;
	private String weixin_templateid;
	
	public TemplateBean() {
		super();
	}
	
	public String getTemplate_id() {
		return template_id;
	}

	public void setTemplate_id(String template_id) {
		this.template_id = template_id;
	}

	public String getTemplate_name() {
		return template_name;
	}

	public void setTemplate_name(String template_name) {
		this.template_name = template_name;
	}

	public String getTemplate_content() {
		return template_content;
	}

	public void setTemplate_content(String template_content) {
		this.template_content = template_content;
	}

	public int getParam_number() {
		return param_number;
	}

	public void setParam_number(int param_number) {
		this.param_number = param_number;
	}

	public String getWeixin_templateid() {
		return weixin_templateid;
	}

	public void setWeixin_templateid(String weixin_templateid) {
		this.weixin_templateid = weixin_templateid;
	}

	@Override
	public String toString() {
		return "TemplateBean [template_id=" + template_id + ", template_name=" + template_name + ", template_content="
				+ template_content + ", param_number=" + param_number + ", weixin_templateid=" + weixin_templateid
				+ "]";
	}


}
