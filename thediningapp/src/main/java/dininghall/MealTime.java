package main.java.dininghall;

import java.util.Collection;
import java.util.HashSet;
import java.time.LocalDate;
import java.sql.ResultSet;
import java.sql.SQLException;
import main.java.com.MySQL;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

public class MealTime {
    private String name;
    private Collection<Kitchen> kitchens;
    private Collection<Dish> dishes;
    private LocalDate date;

    MealTime(String n, Collection<Kitchen> kits) {
        this.setName(n);
        this.kitchens = kits;
    }

    public MealTime(String n, LocalDate d) {
        this.name = n;
        this.date = d;
        this.dishes = getDishes(n, date);
        System.out.println("Dishes size is " + this.dishes.size());
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    MealTime(String n) {
        this.setName(n);
        this.kitchens = new HashSet<Kitchen>();
    }

    void addKitchen(Kitchen k) {
        this.kitchens.add(k);
    }

    public boolean hasData() {
        return this.dishes.size() > 1;
    }

    private Collection<Dish> getDishes(String mt_name, LocalDate date) {
        MySQL mysql = new MySQL();
        ResultSet rs = mysql.getMealTimeDishes(mt_name, date);
        Collection<Dish> dishes = new HashSet<Dish>();

        String dName = null;
        String dhName = null;
        String kName = null;
        String mName = mt_name;
        Dish d = null;

        try {
            while (rs.next()) {
                if (rs.getString("dName").equals(dName)) {
                    d.addAllergy(rs.getString("aName"));
                } else if (rs.getString("dName") != dName && d != null) {
                    dishes.add(d);
                }
                dName = rs.getString("dName");
                dhName = rs.getString("dhName");
                kName = rs.getString("kName");
                mName = rs.getString("mName");
                d = new Dish(dName, dhName, kName, mName);
            }

        } catch (SQLException sqle) {
            sqle.printStackTrace();
        }

        mysql.closeConnection();

        return dishes;
    }

    public JSONArray toJSONArray() {
        JSONArray dArray = new JSONArray();

        for (Dish d : this.dishes) {
            dArray.add(d.toJSON());
        }

        return dArray;
    }

    JSONObject toJSON() {
        JSONObject json = new JSONObject();
        json.put("name", this.getName());
        JSONArray ks = new JSONArray();
        for (Kitchen k : this.kitchens) {
            ks.add(k.toJSON());
        }
        json.put("kitchens", ks);
        return json;
    }

    public Collection<Kitchen> getKitchens() {
        return this.kitchens;
    }

}
