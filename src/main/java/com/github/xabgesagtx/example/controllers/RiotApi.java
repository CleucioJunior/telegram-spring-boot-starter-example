package com.github.xabgesagtx.example.controllers;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.xabgesagtx.example.models.Summoner;
import com.github.xabgesagtx.example.models.SummonerRank;

@Component
@ConfigurationProperties
public class RiotApi {
	
	private static final Logger logger = LoggerFactory.getLogger(RiotApi.class);
	
	@Value("${bot.riotApiToken}")
	private String riotApiToken;// = "?api_key=RGAPI-325fc7f0-8bca-4f43-a4f6-c4c1b0b80419";
	
	@Value("${bot.brRiotHost}")
	private String brRiotHost;// = "https://br1.api.riotgames.com";
	
	@Value("${bot.riotSummonerByName}")
	private String riotSummonerByName;// = "/lol/summoner/v4/summoners/by-name/";
	
	@Value("${bot.riotRankBySummoner}")
	private String riotRankBySummoner;// = "/lol/league/v4/positions/by-summoner/";
	
	public Summoner FindSummonerByName(String name) throws JsonParseException, JsonMappingException, MalformedURLException, IOException{
		String urlApi = brRiotHost + riotSummonerByName + name + riotApiToken;
		ObjectMapper objMapper = new ObjectMapper();
		
		return objMapper.readValue(new URL(urlApi), Summoner.class);
	}
	
	public SummonerRank FindRankBySummoner(String id) throws JsonParseException, JsonMappingException, MalformedURLException, IOException {
		String urlApi = brRiotHost + riotRankBySummoner + id + riotApiToken;
		ObjectMapper objMapper = new ObjectMapper();
		
		List<SummonerRank> list = objMapper.readValue(new URL(urlApi), new TypeReference<List<SummonerRank>>(){});
		
		return list.isEmpty()? null : list.get(0);
	}
	
	@PostConstruct
	public void start() {
		logger.info("Host: {}, RankBySummoner: {}", brRiotHost, riotRankBySummoner);
	}
	
	
	/*
	@Value("${bot.riotApiToken}")
	private static void setRiotApiToken(String riotApiToken) {
		RiotApi.riotApiToken = riotApiToken;
	}
	
	@Value("${bot.brRiotHost}")
	private static void setBrRiotHost(String brRiotHost) {
		RiotApi.brRiotHost = brRiotHost;
	}
	
	@Value("${bot.riotSummonerByName}")
	private static void setRiotSummonerByName(String riotSummonerByName) {
		RiotApi.riotSummonerByName = riotSummonerByName;
	}
	
	@Value("${bot.riotRankBySummoner}")
	private static void setRiotRankBySummoner(String riotRankBySummoner) {
		RiotApi.riotRankBySummoner = riotRankBySummoner;
	}
*/	
	
}
