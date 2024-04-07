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
		t.setWorkoutType("Strength");
		//tr.save(t);
	
		Template t1 = context.getBean(Template.class);
		t1.setUserId(0);
		t1.setId(1);
		t1.setName("PlatePinch");
		t1.setDesc("Grip training for climbing");
		t1.setWorkoutType("Isometric");
		//tr.save(t1);

		Template t2 = context.getBean(Template.class);
		t2.setUserId(0);
		t2.setId(2);
		t2.setName("Running");
		t2.setDesc("I wud runnin");
		t2.setWorkoutType("Cardio");
		//tr.save(t2);
	
		
	Strength s = context.getBean(Strength.class);
	StrengthRepo sr = context.getBean(StrengthRepo.class);
	//Strength stren = (Strength)ConfigurableApplicationContext.getBean("stren",0,1);

	s.setUserId(0);
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


Isometric i = context.getBean(Isometric.class);
	IsometricRepo ir = context.getBean(IsometricRepo.class);
	i.setUserId(0);
	i.setId(2);
	i.setTemplateId(1);
	i.setWeight(new WeightObj((short)45,Unit.LB));
	i.setSet(StrParse.toIsometricSet("0:30,0:29,0:28,0:27,0:26"));
	//ir.save(i);


Cardio c = context.getBean(Cardio.class);
		CardioRepo cr = context.getBean(CardioRepo.class);
		c.setUserId(0);
		c.setId(3);
		c.setTemplateId(2);
		c.setDistance((float)2.1);
		c.setUnit(Unit.MI);
		c.setTags(StrParse.toTagArray("running, fugly, kankles, scuba"));
		//cr.save(c);



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
	


		tr.findAll().forEach((tem)-> System.out.println(tem.toString()));
		ur.findAll().forEach((u) -> System.out.print(u.getName() + ", "));

		//wr.getByTag("bye").forEach((thing) -> System.out.println(thing.getTags()));

		wr.findAll().forEach((workout) -> System.out.println(workout.toString()));
		System.out.println("");
		sr.findAll().forEach((stn) -> System.out.println(stn.toString()));
		ir.findAll().forEach((iso) -> System.out.println(iso.toString()));
		cr.findAll().forEach((crd) -> System.out.println(crd.toString()));





	}
}



