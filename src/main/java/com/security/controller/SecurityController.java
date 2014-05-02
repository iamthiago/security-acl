/**
 * 
 */
package com.security.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.security.service.UserGroupManager;

/**
 * @author Thiago
 *
 */
@Controller
@RequestMapping("security")
public class SecurityController {
	
	@Autowired private UserGroupManager userGroupManager;
	
	@RequestMapping(value = "login/{username}/{group:.*}", method = RequestMethod.GET)
	public String loginViaNds(@PathVariable String username, @PathVariable String group) {
		
		//TODO: Colocar essa lógica dentro do service 
		//TODO: alterar a variavel group pra receber um array de groups
		
		String[] parts = group.split("\\*");
		for (String string : parts) {
			System.out.println(string);
		}
		
		//userGroupManager.createAndAuthenticateUser(username, group);
		return "redirect:/";
	}
	
	@RequestMapping(value = "listAllGroups", method = RequestMethod.GET)
	public @ResponseBody List<String> listAllGroups() {
		return userGroupManager.listAllGroups();
	}
	
	@RequestMapping(value = "listGroupAuthorities/{groupName}", method = RequestMethod.POST)
	public @ResponseBody List<GrantedAuthority> listGroupAuthorities(@PathVariable String groupName) {
		return userGroupManager.listGroupAuthorities(groupName);
	}
	
	@RequestMapping(value = "listAllRoles", method = RequestMethod.GET)
	public @ResponseBody Map<String, String> listAllRoles() {
		Map<String, String> map = new HashMap<>();
		//map.put("ROLE_ADMIN", "Administrador");
		map.put("ROLE_EILD", "Eild");
		map.put("ROLE_TORRES", "Torres");
		map.put("ROLE_MOVEIS", "Moveis");
		map.put("ROLE_CLASSEV", "Classe V");
		map.put("ROLE_BACKHAUL", "Backhaul");
		map.put("ROLE_DUTOS", "Dutos");
		map.put("ROLES_BITSTREAM", "Bitstream");
		map.put("ROLE_UNBUNDLING", "Full Unbundling");
		map.put("ROLE_INTERLIGACAO", "Interligação");
		map.put("ROLE_ROAMING", "Roaming");
		return map;
	}
	
	@RequestMapping(value = "salvarPerfil", method = RequestMethod.POST)
	public @ResponseBody void salvarPerfil(@RequestParam String group, @RequestParam String... roles) {
		userGroupManager.addRolesToGroup(group, roles);
	}
}