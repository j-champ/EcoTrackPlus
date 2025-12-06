package main.model.user;

import main.controller.ActivityController;
import main.model.activity.Activity;
import main.model.activity.RestoredActivity;
import main.model.budget.CarbonBudget;
import main.model.badges.BadgeManager;
import main.model.score.EcoScoreEngine;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;

public class UserManager {

    private static final String BASE_DIR =
            System.getProperty("user.home") + File.separator +
                    "EcoTrackPlus" + File.separator + "users" + File.separator;

    public UserManager() {
        File base = new File(BASE_DIR);
        if (!base.exists()) {
            base.mkdirs();
        }
    }

    public boolean userExists(String username) {
        File f = new File(BASE_DIR + username);
        return f.exists();
    }

    public void registerUser(String username, String password)
            throws UserAlreadyExistsException, IOException {

        if (userExists(username)) {
            throw new UserAlreadyExistsException("User already exists!");
        }

        File userDir = new File(BASE_DIR + username);
        if (!userDir.mkdirs() && !userDir.exists()) {
            throw new IOException("Could not create user directory!");
        }

        try (FileWriter fw = new FileWriter(userDir + "/credentials.txt")) {
            fw.write(username + System.lineSeparator());
            fw.write(hash(password) + System.lineSeparator());
        }

        new File(userDir + "/activity_history.csv").createNewFile();
        new File(userDir + "/badges.txt").createNewFile();

        try (FileWriter fw = new FileWriter(userDir + "/profile.txt")) {
            fw.write("score=0" + System.lineSeparator());
            fw.write("remainingBudget=60.0" + System.lineSeparator());
        }
    }

    public User login(String username, String password)
            throws InvalidCredentialsException, IOException {

        if (!userExists(username)) {
            throw new InvalidCredentialsException("User does not exist!");
        }

        Path credPath = Path.of(BASE_DIR + username + "/credentials.txt");
        if (!Files.exists(credPath)) {
            throw new InvalidCredentialsException("Credentials file missing!");
        }

        var lines = Files.readAllLines(credPath);
        if (lines.size() < 2) {
            throw new InvalidCredentialsException("Corrupted credentials file!");
        }

        String storedUser = lines.get(0).trim();
        String storedHash = lines.get(1).trim();

        if (!storedUser.equals(username) ||
                !storedHash.equals(hash(password))) {
            throw new InvalidCredentialsException("Incorrect username or password!");
        }

        return new User(username, storedHash);
    }

    public void saveProfile(String username,
                            EcoScoreEngine scoreEngine,
                            CarbonBudget budget) {
        File f = new File(BASE_DIR + username + "/profile.txt");
        try (FileWriter fw = new FileWriter(f, false)) {
            fw.write("score=" + scoreEngine.getScore() + System.lineSeparator());
            fw.write("remainingBudget=" + budget.getRemainingBudget() + System.lineSeparator());
        } catch (IOException e) {
            System.out.println("Error saving profile: " + e.getMessage());
        }
    }

    public void loadProfile(String username,
                            EcoScoreEngine scoreEngine,
                            CarbonBudget budget) {
        File f = new File(BASE_DIR + username + "/profile.txt");
        if (!f.exists()) return;

        try (BufferedReader br = new BufferedReader(new FileReader(f))) {
            String line;
            while ((line = br.readLine()) != null) {
                if (line.startsWith("score=")) {
                    int s = Integer.parseInt(line.substring("score=".length()));
                    scoreEngine.setScore(s);
                } else if (line.startsWith("remainingBudget=")) {
                    double rb = Double.parseDouble(line.substring("remainingBudget=".length()));
                    budget.setRemainingBudget(rb);
                }
            }
        } catch (Exception e) {
            System.out.println("Error loading profile: " + e.getMessage());
        }
    }

    public void saveActivity(String username, Activity activity) {
        File f = new File(BASE_DIR + username + "/activity_history.csv");
        try (FileWriter fw = new FileWriter(f, true)) {
            fw.write(activity.getCategory() + "," +
                    activity.getName() + "," +
                    activity.getDate() + "," +
                    activity.calculateEmission() +
                    System.lineSeparator());
        } catch (IOException e) {
            System.out.println("Error saving activity history: " + e.getMessage());
        }
    }

    public void loadActivities(String username, ActivityController controller) {
        File f = new File(BASE_DIR + username + "/activity_history.csv");
        if (!f.exists()) return;

        try (BufferedReader br = new BufferedReader(new FileReader(f))) {
            String row;
            while ((row = br.readLine()) != null) {
                String[] p = row.split(",");
                if (p.length < 4) continue;

                String category = p[0].trim();
                String name = p[1].trim();
                LocalDate date = LocalDate.parse(p[2].trim());
                double emission = Double.parseDouble(p[3].trim());

                Activity restored = new RestoredActivity(category, name, date, emission);
                controller.loadActivity(restored);
            }
        } catch (Exception e) {
            System.out.println("Error loading activities: " + e.getMessage());
        }
    }


    public void saveBadges(String username, BadgeManager badgeManager) {
        File f = new File(BASE_DIR + username + "/badges.txt");
        try (FileWriter fw = new FileWriter(f, false)) {
            for (var badge : badgeManager.getEarnedBadges()) {
                fw.write(badge.getName() + System.lineSeparator());
            }
        } catch (IOException e) {
            System.out.println("Error saving badges: " + e.getMessage());
        }
    }

    public void loadBadges(String username, BadgeManager badgeManager) {
        File f = new File(BASE_DIR + username + "/badges.txt");
        if (!f.exists()) return;

        try {
            var lines = Files.readAllLines(f.toPath());
            for (String name : lines) {
                if (!name.isBlank()) {
                    badgeManager.forceEarn(name.trim());
                }
            }
        } catch (IOException e) {
            System.out.println("Error loading badges: " + e.getMessage());
        }
    }

    private String hash(String p) {
        return Integer.toHexString(p.hashCode());
    }
}

