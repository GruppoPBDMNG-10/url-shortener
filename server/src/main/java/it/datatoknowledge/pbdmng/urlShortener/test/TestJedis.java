package it.datatoknowledge.pbdmng.urlShortener.test;

import redis.clients.jedis.Jedis;

public class TestJedis {

	public TestJedis() {
		// TODO Auto-generated constructor stub
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Jedis jedis = new Jedis();
		jedis.connect();
		jedis.set("foo1", "bar1");
		
		System.out.println(jedis.get("foo1"));
		jedis.close();
	}

}
