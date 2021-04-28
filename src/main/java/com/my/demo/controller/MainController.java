package com.my.demo.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;

@Controller
public class MainController {

	String sSiteCode = "BS177"; // NICE 固定值
	String sSitePassword = "fOUAoZn8N0Ev"; // NICE 固定密码

	@RequestMapping(value = { "/index", "/" })
	public String index() throws JsonMappingException, JsonProcessingException {

		return "index";
	}

	@RequestMapping(value = "/ajax/doCheck")
	@ResponseBody
	public Map doCheck(@RequestParam Map<String, Object> param, HttpServletRequest request, Model model,
			HttpSession session) throws JsonMappingException, JsonProcessingException {

		NiceID.Check.CPClient niceCheck = new NiceID.Check.CPClient();
		String sRequestNumber = "REQ0000000001"; // 请求ID,以便查询成功失败 可以自定义
		sRequestNumber = niceCheck.getRequestNO(sSiteCode); // 也可以用jar里面的方法来获取
		session.setAttribute("REQ_SEQ", sRequestNumber);
		String sAuthType = ""; // 空值初始值 M: 手机, C: 信用卡, X: 公认认证书(韩国专用)

		String popgubun = "N"; // Y : 有取消按钮 / N : 无取消按钮
		String customize = ""; // 空值是网页 / Mobile : 手机端网页

		String sGender = ""; // 空值初始值, 0 : 女, 1 : 男

		// 认证后需要获取返回值
		// 返回地址必须要相同 ex) 认证前 url : http://www.~ 认证 url : http://www.~
		String sReturnUrl = "http://localhost:8080/sucess"; // 成功后返回的URL
		String sErrorUrl = "http://localhost:8080/fail"; // 失败后返回的URL

		// 需要以下格式来制作数据
		String sPlainData = "7:REQ_SEQ" + sRequestNumber.getBytes().length + ":" + sRequestNumber + "8:SITECODE"
				+ sSiteCode.getBytes().length + ":" + sSiteCode + "9:AUTH_TYPE" + sAuthType.getBytes().length + ":"
				+ sAuthType + "7:RTN_URL" + sReturnUrl.getBytes().length + ":" + sReturnUrl + "7:ERR_URL"
				+ sErrorUrl.getBytes().length + ":" + sErrorUrl + "11:POPUP_GUBUN" + popgubun.getBytes().length + ":"
				+ popgubun + "9:CUSTOMIZE" + customize.getBytes().length + ":" + customize + "6:GENDER"
				+ sGender.getBytes().length + ":" + sGender;

		String sMessage = "";
		String sEncData = "";

		int iReturn = niceCheck.fnEncode(sSiteCode, sSitePassword, sPlainData);
		if (iReturn == 0) {
			sEncData = niceCheck.getCipherData();
		} else if (iReturn == -1) {
			sMessage = "암호화 시스템 에러입니다."; // 加密系统出错
		} else if (iReturn == -2) {
			sMessage = "암호화 처리오류입니다."; // 加密过程出错
		} else if (iReturn == -3) {
			sMessage = "암호화 데이터 오류입니다."; // 机密数据出错
		} else if (iReturn == -9) {
			sMessage = "입력 데이터 오류입니다."; // 输入数据出错
		} else {
			sMessage = "알수 없는 에러 입니다. iReturn : " + iReturn; // 其他错误
		}
		
		Map data = new HashMap();
		data.put("sMessage", sMessage);
		data.put("sEncData", sEncData);
		return data;
	}

	@RequestMapping(value = { "/sucess" })
	public String sucess(@RequestParam Map<String, Object> param, HttpServletRequest request, Model model,
			HttpSession session) throws JsonMappingException, JsonProcessingException {

		NiceID.Check.CPClient niceCheck = new NiceID.Check.CPClient();

		String sEncodeData = requestReplace(request.getParameter("EncodeData"), "encodeData");

		String sCipherTime = ""; //解密时间
		String sRequestNumber = ""; // 请求ID
		String sResponseNumber = ""; // 认证固定ID
		String sAuthType = ""; // 认证方法
		String sName = ""; // 姓名
		String sDupInfo = ""; // 重发加入确认值(DI_64 byte)
		String sConnInfo = ""; // 链接确认值 (CI_88 byte)
		String sBirthDate = ""; // 生日(YYYYMMDD)
		String sGender = ""; // 性别
		String sNationalInfo = ""; //国内/海外 (개발가이드 참조)
		String sMobileNo = ""; // 手机号
		String sMobileCo = ""; // 通信公司
		String sMessage = "";
		String sPlainData = "";

		int iReturn = niceCheck.fnDecode(sSiteCode, sSitePassword, sEncodeData);

		if (iReturn == 0) {
			sPlainData = niceCheck.getPlainData();
			sCipherTime = niceCheck.getCipherDateTime();

			// 데이타를 추출합니다.
			HashMap mapresult = niceCheck.fnParse(sPlainData);

			sRequestNumber = (String) mapresult.get("REQ_SEQ");
			sResponseNumber = (String) mapresult.get("RES_SEQ");
			sAuthType = (String) mapresult.get("AUTH_TYPE");
			sName = (String) mapresult.get("NAME");
			// sName = (String)mapresult.get("UTF8_NAME"); //charset utf8 사용시 주석 해제 후 사용
			sBirthDate = (String) mapresult.get("BIRTHDATE");
			sGender = (String) mapresult.get("GENDER");
			sNationalInfo = (String) mapresult.get("NATIONALINFO");
			sDupInfo = (String) mapresult.get("DI");
			sConnInfo = (String) mapresult.get("CI");
			sMobileNo = (String) mapresult.get("MOBILE_NO");
			sMobileCo = (String) mapresult.get("MOBILE_CO");

			String session_sRequestNumber = (String) session.getAttribute("REQ_SEQ");
			if (!sRequestNumber.equals(session_sRequestNumber)) {
				sMessage = "세션값 불일치 오류입니다.";	// sessiong 值不一致
				sResponseNumber = "";
				sAuthType = "";
			}
		} else if (iReturn == -1) {
			sMessage = "복호화 시스템 오류입니다."; // 解密系统出错
		} else if (iReturn == -4) {
			sMessage = "복호화 처리 오류입니다.";	// 解密过程出错
		} else if (iReturn == -5) {
			sMessage = "복호화 해쉬 오류입니다.";	// 解密hash值出错
		} else if (iReturn == -6) {
			sMessage = "복호화 데이터 오류입니다."; //解密数据出错
		} else if (iReturn == -9) {
			sMessage = "입력 데이터 오류입니다.";	//输入值出错
		} else if (iReturn == -12) {
			sMessage = "사이트 패스워드 오류입니다.";	//ID密码出错
		} else {
			sMessage = "알수 없는 에러 입니다. iReturn : " + iReturn; //其他错误
		}

		model.addAttribute("sCipherTime", sCipherTime);
		model.addAttribute("sRequestNumber", sRequestNumber);
		model.addAttribute("sResponseNumber", sResponseNumber);
		model.addAttribute("sAuthType", sAuthType);
		model.addAttribute("sName", sName);
		model.addAttribute("sDupInfo", sDupInfo);
		model.addAttribute("sConnInfo", sConnInfo);
		model.addAttribute("sBirthDate", sBirthDate);
		model.addAttribute("sGender", sGender);
		model.addAttribute("sNationalInfo", sNationalInfo);
		model.addAttribute("sMobileNo", sMobileNo);
		model.addAttribute("sMobileCo", sMobileCo);
		return "sucess";
	}

	@RequestMapping(value = { "/fail" })
	public String fail(@RequestParam Map<String, Object> param, HttpServletRequest request, Model model,
			HttpSession session) throws JsonMappingException, JsonProcessingException {

		NiceID.Check.CPClient niceCheck = new NiceID.Check.CPClient();

		String sEncodeData = requestReplace(request.getParameter("EncodeData"), "encodeData");
		String sCipherTime = ""; // 解密时间
		String sRequestNumber = ""; // 请求ID
		String sErrorCode = ""; // 认证结果值
		String sAuthType = ""; // 认证方法
		String sMessage = "";
		String sPlainData = "";

		int iReturn = niceCheck.fnDecode(sSiteCode, sSitePassword, sEncodeData);

		if (iReturn == 0) {
			sPlainData = niceCheck.getPlainData();
			sCipherTime = niceCheck.getCipherDateTime();
			// 데이타를 추출합니다.
			HashMap mapresult = niceCheck.fnParse(sPlainData);
			sRequestNumber = (String) mapresult.get("REQ_SEQ");
			sErrorCode = (String) mapresult.get("ERR_CODE");
			sAuthType = (String) mapresult.get("AUTH_TYPE");
		} else if (iReturn == -1) {
			sMessage = "복호화 시스템 에러입니다.";	// 解密系统出错
		} else if (iReturn == -4) {
			sMessage = "복호화 처리오류입니다.";		// 解密过程出错
		} else if (iReturn == -5) {
			sMessage = "복호화 해쉬 오류입니다.";		//解密hash值出错
		} else if (iReturn == -6) {
			sMessage = "복호화 데이터 오류입니다.";	//解密数据出错
		} else if (iReturn == -9) {
			sMessage = "입력 데이터 오류입니다.";		//输入值出错
		} else if (iReturn == -12) {
			sMessage = "사이트 패스워드 오류입니다.";	//ID密码出错
		} else {
			sMessage = "알수 없는 에러 입니다. iReturn : " + iReturn;	//其他错误
		}

		model.addAttribute("sCipherTime", sCipherTime);
		model.addAttribute("sRequestNumber", sRequestNumber);
		model.addAttribute("sErrorCode", sErrorCode);
		model.addAttribute("sAuthType", sAuthType);
		model.addAttribute("sMessage", sMessage);
		model.addAttribute("sPlainData", sPlainData);
		
		return "fail";
	}

	public String requestReplace(String paramValue, String gubun) {

		String result = "";

		if (paramValue != null) {

			paramValue = paramValue.replaceAll("<", "&lt;").replaceAll(">", "&gt;");

			paramValue = paramValue.replaceAll("\\*", "");
			paramValue = paramValue.replaceAll("\\?", "");
			paramValue = paramValue.replaceAll("\\[", "");
			paramValue = paramValue.replaceAll("\\{", "");
			paramValue = paramValue.replaceAll("\\(", "");
			paramValue = paramValue.replaceAll("\\)", "");
			paramValue = paramValue.replaceAll("\\^", "");
			paramValue = paramValue.replaceAll("\\$", "");
			paramValue = paramValue.replaceAll("'", "");
			paramValue = paramValue.replaceAll("@", "");
			paramValue = paramValue.replaceAll("%", "");
			paramValue = paramValue.replaceAll(";", "");
			paramValue = paramValue.replaceAll(":", "");
			paramValue = paramValue.replaceAll("-", "");
			paramValue = paramValue.replaceAll("#", "");
			paramValue = paramValue.replaceAll("--", "");
			paramValue = paramValue.replaceAll("-", "");
			paramValue = paramValue.replaceAll(",", "");

			if (gubun != "encodeData") {
				paramValue = paramValue.replaceAll("\\+", "");
				paramValue = paramValue.replaceAll("/", "");
				paramValue = paramValue.replaceAll("=", "");
			}

			result = paramValue;

		}
		return result;
	}

	@RequestMapping(value = { "/checkplus_main" })
	public String checkplus_main(@RequestParam Map<String, Object> param, HttpServletRequest request, Model model)
			throws JsonMappingException, JsonProcessingException {

		return "back/checkplus_main";
	}

	@RequestMapping(value = { "/checkplus_fail" })
	public String checkplus_fail(@RequestParam Map<String, Object> param, HttpServletRequest request, Model model)
			throws JsonMappingException, JsonProcessingException {

		return "back/checkplus_fail";
	}

	@RequestMapping(value = { "/checkplus_success" })
	public String checkplus_success(@RequestParam Map<String, Object> param, HttpServletRequest request, Model model)
			throws JsonMappingException, JsonProcessingException {

		return "back/checkplus_success";
	}

}