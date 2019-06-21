package com.task.webservices;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.task.entity.Player;
import com.task.exception.PlayerDataAccessException;
import com.task.exception.RecordNotFoundException;
import com.task.service.FileDirectoryServiceImpl;
import com.task.service.PlayerWebService;

@RestController
@RequestMapping("/api")
public class PlayerWebController {
	private static final Logger logger = LoggerFactory.getLogger(FileDirectoryServiceImpl.class);
	@Autowired
	PlayerWebService playerWebService;

	@PostMapping(path = "/players", produces = { MediaType.APPLICATION_JSON_VALUE })
	public List<Player> playerRestservice(@RequestBody Player player) {
		try {
			if (playerWebService.findCustomPayer(player).isEmpty())
				throw new RecordNotFoundException("Player Record not found :" + player);
			return playerWebService.findCustomPayer(player);
		} catch (PlayerDataAccessException e) {
			e.printStackTrace();
		}

		return null;
	}
}