package com.LibreGainz.gainzserver;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import com.LibreGainz.gainzserver.model.*;
import com.LibreGainz.gainzserver.repo.*;
import java.util.ArrayList;
@SpringBootApplication
public class GainzserverApplication {
	private static short s1 = 180;

	public static void main(String[] args) {


		ConfigurableApplicationContext context = SpringApplication.run(GainzserverApplication.class, args);

		//User tina = context.getBean(User.class);
		//tina.setName("Tina");
		//tina.setId(101);
		UserRepo ur = context.getBean(UserRepo.class);
		//ur.save(tina);



		//Template t = context.getBean(Template.class);
		//TemplateRepo tr = context.getBean(TemplateRepo.class);
		//t.setUserId(101);
		//t.setId(0);
		//t.setName("Deadlift");
		//tr.save(t);
	


		
		//Strength s = context.getBean(Strength.class);
		//StrengthRepo sr = context.getBean(StrengthRepo.class);
		//s.setId(0);
		//s.setWeight(s1);
		//s.setUnit(Unit.KG);
		//s.setSet(set1);
		//sr.save(s);

		//ArrayList<String> tags = new ArrayList<String>();
		//tags.add("helo");
		//tags.add("hi");
		//tags.add("bye");


		WorkoutRepo wr = context.getBean(WorkoutRepo.class);

		long millis=System.currentTimeMillis(); 
		java.sql.Date date = new java.sql.Date(millis); 

		//Workout w = context.getBean(Workout.class);
		//w.setTags(tags);
		//w.setTemplateId(0);
		//w.setDate(date);
		//wr.save(w);


		//ArrayList<String> tags2 = new ArrayList<String>();
		//tags2.add("bruh");
		//tags2.add("tags and stuff");
		//Workout w2 = context.getBean(Workout.class);
		//w2.setTags(tags2);
		//w2.setId(3);
		//w2.setTemplateId(0);
		//w2.setDate(date);
		//wr.save(w2);
	


		//System.out.println(sr.findAll());
		//System.out.println(tr.findAll());
		ur.findAll().forEach((u) -> System.out.println(u.getName()));

		wr.getByTag("tags and stuff").forEach((thing) -> System.out.println(thing.getTags()));

		wr.findAll().forEach((workout) -> System.out.println(workout.getId()
		+ " " + workout.getDate().toString()
		 + " " + workout.getTags()));




	}
}



