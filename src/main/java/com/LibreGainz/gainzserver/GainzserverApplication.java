package com.LibreGainz.gainzserver;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import com.LibreGainz.gainzserver.model.*;
import com.LibreGainz.gainzserver.repo.*;
import com.LibreGainz.gainzserver.model.WeightObj;
import java.util.ArrayList;
@SpringBootApplication
public class GainzserverApplication {
	private static short s1 = 180;

	public static void main(String[] args) {


		ConfigurableApplicationContext context = SpringApplication.run(GainzserverApplication.class, args);
		User tina = context.getBean(User.class);
		tina.setName("Tina");
		tina.setId(0);
		UserRepo ur = context.getBean(UserRepo.class);
		//ur.save(tina);



		TemplateRepo tr = context.getBean(TemplateRepo.class);
		Template t = context.getBean(Template.class);
		t.setUserId(0);
		t.setId(0);
		t.setName("Deadlift");
		t.setDesc("Phat lifto");
		//tr.save(t);
	


		
	Strength s = context.getBean(Strength.class);
	StrengthRepo sr = context.getBean(StrengthRepo.class);
	//Strength stren = (Strength)ConfigurableApplicationContext.getBean("stren",0,1);
	s.setId(1);
	s.setTemplateId(0);
	s.setWeight(new WeightObj(s1,Unit.KG));

	

	ArrayList<String> tags = new ArrayList<String>();
	tags.add("helo");
	tags.add("hi");
	tags.add("bye");
	s.setTags(tags);

	ArrayList<Short> repsArr = new ArrayList<Short>();
	repsArr.add((short)2);
	repsArr.add((short)9);
	repsArr.add((short)2);
	repsArr.add((short)7);
	s.setSet(repsArr);
	//sr.save(s);



		WorkoutRepo wr = context.getBean(WorkoutRepo.class);

	//	long millis=System.currentTimeMillis(); 
	//	java.sql.Date date = new java.sql.Date(millis); 


	//	Workout w = context.getBean(Workout.class);
	//	w.setTags(tags);
	//	w.setTemplateId(0);
	//	w.setDate(date);
	//	wr.save(w);


	//	ArrayList<String> tags2 = new ArrayList<String>();
	//	tags2.add("bruh");
	//	tags2.add("tags and stuff");
	//	Workout w2 = context.getBean(Workout.class);
	//	w2.setTags(tags2);
	//	w2.setId(3);
	//	w2.setTemplateId(0);
	//	w2.setDate(date);
	//	wr.save(w2);
	


		ur.findAll().forEach((u) -> System.out.print(u.getName() + ", "));

		//wr.getByTag("bye").forEach((thing) -> System.out.println(thing.getTags()));

		wr.findAll().forEach((workout) -> System.out.println(workout.toString()));
		tr.findAll().forEach((tem)-> System.out.println(tem.toString()));





	}
}



