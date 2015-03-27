package se.kth.myseries.server.persistence;

import java.util.ArrayList;
import java.util.Calendar;

import javax.jdo.PersistenceManager;

import se.kth.myseries.server.persistence.entity.Episode;
import se.kth.myseries.server.persistence.entity.Season;
import se.kth.myseries.server.persistence.entity.Serie;

import com.google.appengine.api.datastore.Text;

/**
 * MySeries <br>
 * 
 * @author Marcelo
 * @since 08/10/2010
 */
public class FakeDataCreator {
	
	public static void createFakeData() {
		createFakeSeriesData();
	}
	
	public static void createFakeSeriesData() {
		
		System.out.println("creating fake data");
		
		String description = "Leonard and Sheldon visit a high IQ sperm bank, but have regrets about going, so they leave, only to feel guilty afterwards. When they arrive back at the apartment, they see a beautiful blond woman, Penny, in the apartment across the hallway and they attempt to befriend her. Sheldon, who has no experience being around someone like Penny, doesn’t know what to do, while Leonard really takes a shine to her. Meanwhile, Howard makes numerous attempts to impress Penny, while Koothrappali is too shy to talk to her.";
		Calendar c = Calendar.getInstance();
		c.set(2007, 8, 24);
		Episode episode1 = new Episode(1, "Pilot", new Text(description), 25,
				c.getTime(), null);
		
		String description2 = "Leonard and Penny return from their date (see ending of episode 1-17), but it ends in an odd matter when Penny wants to take things slower. While Leonard is living his normal everyday life, Penny is now worrying if she is smart enough for Leonard, as she has none of the same interests as him, so she seeks advice from Sheldon and even tells him a secret. However, when it turns out Sheldon can’t keep her secret, the relationship between Leonard and Penny may be broken forever.";
		Calendar c2 = Calendar.getInstance();
		c.set(2008, 8, 22);
		Episode episode2 = new Episode(18, "The Bad Fish Paradigm", new Text(
				description2), 25, c2.getTime(), null);
		
		ArrayList<Episode> episodes = new ArrayList<Episode>();
		episodes.add(episode1);
		Season season1 = new Season(1, 2007, null, episodes);
		episode1.setSeason(season1);
		
		ArrayList<Episode> episodes2 = new ArrayList<Episode>();
		episodes2.add(episode2);
		Season season2 = new Season(2, 2008, null, episodes2);
		episode2.setSeason(season2);
		
		String description3 = "The Big Bang Theory is a comedy series about four young scientists who know all about the world of physics, and one girl, who gives the physics world a real spin. Leonard Hofstadter is a smart guy who tries his best to complete his research and become famous for his work while also attempting to be the most socially-interacting guy in their friend group. Sheldon Cooper is one of the world’s smartest men with an intellectual capacity through the roof and a language with scientific words normal people only have one comment to (“What?”). Though it’s never been said by himself, all signs throughout the show point to him having the Asperger Syndrome, making him the smartest, but also least social, group member. Howard Wolowitz is an engineer with his mind partially on science, but mostly on women. He always tries his best to impress a girl and get his share of sexual intercourses, but he always manages to screw up somehow, probably with his bad knowledge of treating women in a bad matter. Rajesh Koothrappali is an Indian scientist performing experiments on black holes, outer space, life on other planets and not to mention string theory. Raj is mostly known for his Selective Mutism, in other words, his fear of speaking to women, making social interaction difficult for him. Finally, there’s Penny. Penny is the gorgeous girl next-door to Leonard and Sheldon’s apartment, and though she does not have any knowledge in physics or science, she makes success by being a funny character frequently having hilarious comments and on- and off-going relationships. All together, this unit of comedians make the show’s half-hour episodes pure enjoyment and whether you like physics, women or neither, this show is surely going to get you laughing!";
		ArrayList<Season> seasons = new ArrayList<Season>();
		seasons.add(season1);
		seasons.add(season2);
		Serie bigBang = new Serie("The Big Bang Theory",
				new Text(description3), seasons);
		
		season1.setSerie(bigBang);
		season2.setSerie(bigBang);
		
		PersistenceManager pm = PMF.getManager();
		try {
			pm.makePersistent(bigBang);
		} finally {
			pm.close();
		}
		
		System.out.println("fake data created");
		
	}
}
