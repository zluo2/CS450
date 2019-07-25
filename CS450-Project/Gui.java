/**
 * Author:  Zhiwen Luo
 * Date:    11/26/2017
 * Purpose: Project Part 4-GUI
**/

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;


import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Iterator;

public class Gui extends Application {

    private Part4 part4;
    private boolean connStatus;
    private boolean siPressed = false;
    private boolean dbConnected = false;
    
    public static void main(String[] args) {  
        launch(args);  
     }  

    @Override
    public void start(Stage primaryStage) {

        primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent event) {
                part4.closeConnection();
            }
        });
        primaryStage.setTitle("Employee Registration Assistant");


        part4 = new Part4();

        this.dbLogin(primaryStage);

        if(dbConnected) {
            this.login(primaryStage);
        }

    }

    public void dbLogin(Stage stage) {
        // add layout
        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);                  // center on screen
        grid.setHgap(10);                               // manage spacing between rows
        grid.setVgap(10);                               //     and columns
        grid.setPadding(new Insets(25, 25, 25, 25));    // manage space around edges of grid pane

        //     top, right, bottom, left

        // add text, labels, and text fields
        Text scenetitle = new Text("Database Login");
        scenetitle.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
        grid.add(scenetitle, 0, 0, 2, 1);
        //grid.setGridLinesVisible(true);

        Label username = new Label("User Name:");
        grid.add(username, 0, 1);

        TextField userTextField = new TextField();
        grid.add(userTextField, 1, 1);

        Label pw = new Label("Password:");
        grid.add(pw, 0, 2);

        PasswordField pwBox = new PasswordField();
        grid.add(pwBox, 1, 2);

        // add sign in button
        Button btn = new Button("Sign in");
        HBox hbBtn = new HBox(10);
        hbBtn.setAlignment(Pos.BOTTOM_RIGHT);
        hbBtn.getChildren().add(btn);
        grid.add(hbBtn, 1, 4);

        // add text to update on status of sign in button
        final Text actiontarget = new Text();
        grid.add(actiontarget, 1, 6);

        // handle sign in by pressing enter on textfield and password field
        userTextField.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                if (event.getCode().equals(KeyCode.ENTER)) {
                    connStatus = part4.connectToDatabase(userTextField.getText(), pwBox.getText());
                    if (connStatus) {
                        dbConnected = true;
                        login(stage);
                    } else {
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setTitle("Connection Error");
                        alert.setHeaderText("Error Connecting to Database");
                        alert.setContentText("No connection to the database was established.\n" +
                                "\nUser name and password may be incorrect, or the database may not be available at this time.\n" +
                                "\nPlease check your connection settings and try again.");
                        alert.showAndWait();
                    }
                }
            }
        });

        pwBox.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                if (event.getCode().equals(KeyCode.ENTER)) {
                    connStatus = part4.connectToDatabase(userTextField.getText(), pwBox.getText());
                    if (connStatus) {
                        dbConnected = true;
                        login(stage);
                    } else {
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setTitle("Connection Error");
                        alert.setHeaderText("Error Connecting to Database");
                        alert.setContentText("No connection to the database was established.\n" +
                                "\nUser name and password may be incorrect, or the database may not be available at this time.\n" +
                                "\nPlease check your connection settings and try again.");
                        alert.showAndWait();
                    }
                }
            }
        });

        // handle click sign in clicked
        btn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                connStatus = part4.connectToDatabase(userTextField.getText(), pwBox.getText());
                if (connStatus) {
                    dbConnected = true;
                    login(stage);
                } else {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Connection Error");
                    alert.setHeaderText("Error Connecting to Database");
                    alert.setContentText("No connection to the database was established.\n" +
                            "\nUser name and password may be incorrect, or the database may not be available at this time.\n" +
                            "\nPlease check your connection settings and try again.");
                    alert.showAndWait();
                }
            }
        });

        // handle sign in by pressing enter
        btn.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                if (event.getCode().equals(KeyCode.ENTER)) {
                    connStatus = part4.connectToDatabase(userTextField.getText(), pwBox.getText());
                    if (connStatus) {
                        dbConnected = true;
                        login(stage);
                    } else {
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setTitle("Connection Error");
                        alert.setHeaderText("Error Connecting to Database");
                        alert.setContentText("No connection to the database was established.\n" +
                                "\nUser name and password may be incorrect, or the database may not be available at this time.\n" +
                                "\nPlease check your connection settings and try again.");
                        alert.showAndWait();
                    }
                }
            }
        });

        stage.setScene(new Scene(grid, 400, 300));
        stage.show();
    }

    public void login(Stage stage) {

        // local vars
        boolean validated = false;

        // add layout
        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);                  // center on screen
        grid.setHgap(10);                               // manage spacing between rows
        grid.setVgap(10);                               //     and columns
        grid.setPadding(new Insets(25, 25, 25, 25));    // manage space around edges of grid pane
        //     top, right, bottom, left

        // add text, labels, and text fields
        Text scenetitle = new Text("Manager Login");
        scenetitle.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
        grid.add(scenetitle, 0, 0, 2, 1);
        //grid.setGridLinesVisible(true);

        Label username = new Label("SSN:");
        grid.add(username, 0, 1);

        TextField userTextField = new TextField();
        grid.add(userTextField, 1, 1);

        // add sign in button
        Button btn = new Button("Sign in");
        HBox hbBtn = new HBox(10);
        hbBtn.setAlignment(Pos.BOTTOM_RIGHT);
        hbBtn.getChildren().add(btn);
        grid.add(hbBtn, 1, 2);

        // add text to update on status of sign in button
        final Text actiontarget = new Text();
        grid.add(actiontarget, 1, 6);

        // handle sign in by pressing enter on test field
        userTextField.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                if (event.getCode().equals(KeyCode.ENTER)) {
                    if (part4.validateManager(Long.parseLong(userTextField.getText()))) {
                        System.out.println("SSN successfully validated");
                        addEmployee(stage);
                    } else {
                        System.out.println("SSN invalid.");
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setTitle("Validation Error");
                        alert.setHeaderText("SSN Invalid");
                        alert.setContentText("The given SSN does not match that of any manager on record.\n" +
                                "\nPlease check the SSN entered and try again.\n" +
                                "\nIf you feel you have reached this screen in error, please contact the system" +
                                "administrator to ensure your information is correct.");
                        alert.showAndWait();
                    }
                }
            }
        });

        // handle sign in clicked
        btn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {

                if (part4.validateManager(Long.parseLong(userTextField.getText()))) {
                    System.out.println("SSN successfully validated");
                    addEmployee(stage);
                } else {
                    System.out.println("SSN invalid.");
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Validation Error");
                    alert.setHeaderText("SSN Invalid");
                    alert.setContentText("The given SSN does not match that of any manager on record.\n" +
                            "\nPlease check the SSN entered and try again.\n" +
                            "\nIf you feel you have reached this screen in error, please contact the system\n " +
                            "administrator to ensure your information is correct.");
                    alert.showAndWait();
                }
            }
        });

        // handle sign in by pressing enter
        btn.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                if (event.getCode().equals(KeyCode.ENTER)) {
                    if (part4.validateManager(Long.parseLong(userTextField.getText()))) {
                        System.out.println("SSN successfully validated");
                        addEmployee(stage);
                    } else {
                        System.out.println("SSN invalid.");
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setTitle("Validation Error");
                        alert.setHeaderText("SSN Invalid");
                        alert.setContentText("The given SSN does not match that of any manager on record.\n" +
                                "\nPlease check the SSN entered and try again.\n" +
                                "\nIf you feel you have reached this screen in error, please contact the system" +
                                "administrator to ensure your information is correct.");
                        alert.showAndWait();
                    }
                }
            }
        });

        Scene scene = new Scene(grid, 400, 300);
        stage.setScene(scene);
        stage.show();
//        return new Scene(grid, 400, 300);
    }

    public void addEmployee(Stage stage) {

        boolean submitIsValid = false;

        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(25, 10, 25, 10));

        // add Title
        Text scenetitle = new Text("Add Employee");
        scenetitle.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
        grid.add(scenetitle, 0, 0, 2, 1);

        // SSN Text Box
        Label username = new Label("SSN:");
        grid.add(username, 0, 1);
        TextField ssnBox = new TextField();
        grid.add(ssnBox, 1, 1);

        // First name text box
        Label firstname = new Label("First Name:");
        grid.add(firstname, 0, 2);
        TextField fnameBox = new TextField();
        grid.add(fnameBox, 1, 2);

        // Middle initial text box
        Label mInit = new Label("Middle Initial:");
        grid.add(mInit, 0, 3);
        TextField minitBox = new TextField();
        minitBox.addEventFilter(KeyEvent.KEY_TYPED, maxLength(1));  // event filter to allow max of
        grid.add(minitBox, 1, 3);                                   // one char entered

        // Last Name text box
        Label lastName = new Label("Last Name:");
        grid.add(lastName, 0, 4);
        TextField lnameBox = new TextField();
        grid.add(lnameBox, 1, 4);

        // Birth Date box and picker
        Label birthDate = new Label("Birth Date:");
        grid.add(birthDate, 0, 5);
        DatePicker bdatePicker = new DatePicker();
        String pattern = "dd-MMM-yy";
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);
        bdatePicker.setPromptText("Select from Calendar ->");
        grid.add(bdatePicker, 1, 5);

        // add Address text box
        Label address = new Label("Address:");
        grid.add(address, 0, 6);
        TextField addressBox = new TextField();
        grid.add(addressBox, 1, 6);

        // Add sex drop down
        Label sex = new Label("Sex:");
        grid.add(sex, 0, 7);
        ChoiceBox sBox = new ChoiceBox();
        sBox.setItems(FXCollections.observableArrayList("M", "F"));
        grid.add(sBox, 1, 7);

        // add salary field
        Label salary = new Label("Salary:");
        grid.add(salary, 0, 8);
        TextField salBox = new TextField();
        grid.add(salBox, 1, 8);

        // add supervisor SSN field
        Label superSsn = new Label("Supervisor SSN:");
        grid.add(superSsn, 0, 9);
        TextField sSsnBox = new TextField();
        grid.add(sSsnBox, 1, 9);

        // add department number box
        Label department = new Label("Department:");
        grid.add(department, 0, 10);
        ChoiceBox depBox = new ChoiceBox();
        depBox.setItems(FXCollections.observableArrayList("1", "4", "5"));
        grid.add(depBox, 1, 10);

        // add Submit button
        Button btn = new Button("Submit");
        HBox hbBtn = new HBox(10);
        hbBtn.setAlignment(Pos.BOTTOM_RIGHT);
        hbBtn.getChildren().add(btn);
        grid.add(hbBtn, 1, 11);

        btn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                String fname = fnameBox.getText();
                String minit = minitBox.getText();
                String lname = lnameBox.getText();
                String ssn   = ssnBox.getText();
                String bdate = null;


                if (bdatePicker.getValue() != null) {
                    bdate = bdatePicker.getValue().format(formatter).toUpperCase();
                    System.out.println(bdate);
                } else {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Date Format Error");
                    alert.setHeaderText("Error Formatting Date of Birth");
                    alert.setContentText("Please select a date chosen from the calendar. This" +
                            " can be done by clicking on the calendar icon on the right.");
                    alert.showAndWait();
                    return;
                }

                String addr = addressBox.getText();
                String sex = sBox.getValue().toString();
                String salary = salBox.getText();
                String super_ssn = sSsnBox.getText();
                String dno = depBox.getValue().toString();

                part4.addEmployee(fname, minit, lname, ssn, bdate, addr, sex, salary,
                        super_ssn, dno);

                // new scene
                assignToProjects(stage, ssn);
            }
        });

        Scene scene = new Scene(grid, 400, 500);
        stage.setScene(scene);
        stage.show();
    }

    public void assignToProjects(Stage stage, String ssn) {

        // setup layout
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(25, 10, 25, 10));

        // add Title
        Text scenetitle = new Text("Assign Hours to Project(s)");
        scenetitle.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
        grid.add(scenetitle, 0, 0, 2, 1);

        // store all project names associated with new employees dept
        ArrayList<String> projectsList = part4.getProjects(ssn);

        // ArrayList to store references to added projects
        ArrayList<TextField> textFields = new ArrayList<>();

        int txtFldCount = 0;
        int rowCount = 1;

        for(int index = 0; index < projectsList.size(); index+=2) {
            Label project = new Label(projectsList.get(index));
            grid.add(project, 0, rowCount);
            textFields.add(new TextField());
            textFields.get(txtFldCount).setText("0");
            grid.add(textFields.get(txtFldCount), 1, rowCount);
            rowCount++;
            txtFldCount++;
        }

        // add checkbox for dependents
        CheckBox depBox = new CheckBox();
        depBox.setText("Has Dependents");
        grid.add(depBox, 1, ++rowCount);

        // add sign in button
        Button btn = new Button("Submit");
        HBox hbBtn = new HBox(10);
        hbBtn.setAlignment(Pos.BOTTOM_RIGHT);
        hbBtn.getChildren().add(btn);
        grid.add(hbBtn, 1, ++rowCount);

        // action if clicked
        btn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {

                Iterator<TextField> itr = textFields.iterator();
                double sumOfHours = 0;
                while (itr.hasNext()) {
                    sumOfHours += Double.parseDouble(itr.next().getText());
                }

                if (sumOfHours > 40) {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Hours Error");
                    alert.setHeaderText("Maximum Hours Exceeded");
                    alert.setContentText("Too many hours have been assigned to this employee. " +
                            "Please keep working hours fewer than or equal to forty (40) hours" +
                            " per week");
                    alert.showAndWait();
                } else {
                    ArrayList<String> projects = new ArrayList<String>();
                    Iterator<TextField> txtFlsItr = textFields.iterator();
                    Iterator<String> projItr = projectsList.iterator();

                    while (projItr.hasNext()) {
                        projects.add(projItr.next());
                        projects.add(projItr.next());
                        projects.add(txtFlsItr.next().getText());
                    }

                    part4.addProjectHours(ssn, projects);

                    if (depBox.isSelected()) {
                        System.out.println("Employee projects successfully added.");
                        addDependents(stage, ssn);
                    } else {
                        System.out.println("Employee projects successfully added.");
                        printReport(stage, ssn, false);
                    }
                }
            }
        });

        // action if enter is pressed
        btn.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                if (event.getCode().equals(KeyCode.ENTER)) {
                    Iterator<TextField> itr = textFields.iterator();
                    double sumOfHours = 0;
                    while (itr.hasNext()) {
                        sumOfHours += Double.parseDouble(itr.next().getText());
                    }

                    if (sumOfHours > 40) {
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setTitle("Hours Error");
                        alert.setHeaderText("Maximum Hours Exceeded");
                        alert.setContentText("Too many hours have been assigned to this employee. " +
                                "Please keep working hours fewer than or equal to forty (40) hours" +
                                " per week");
                        alert.showAndWait();
                    } else {
                        // TODO
                        ArrayList<String> projects = new ArrayList<String>();
                        Iterator<TextField> txtFlsItr = textFields.iterator();
                        Iterator<String> projItr = projectsList.iterator();

                        while (projItr.hasNext()) {
                            projects.add(projItr.next());
                            projects.add(projItr.next());
                            projects.add(txtFlsItr.next().getText());
                        }

                        part4.addProjectHours(ssn, projects);

                        if (depBox.isSelected()) {
                            System.out.println("Employee projects successfully added.");
                            addDependents(stage, ssn);
                        } else {
                            System.out.println("Employee projects successfully added.");
                            printReport(stage, ssn, false);
                        }
                    }
                }
            }
        });

        stage.setScene(new Scene(grid, 400, 300));
        stage.show();

    }

    public void printReport(Stage stage, String ssn, boolean hasDependents) {

        // setup layout
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(25, 10, 25, 10));

        // add Title
        Text scenetitle = new Text("New Employee Report");
        scenetitle.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
        grid.add(scenetitle, 0, 0, 2, 1);

        // section 1: employee info
        Label empInfo = new Label("Employee Information:");
        grid.add(empInfo, 0, 1);

        String[] infoLabels = {"First Name: ", "Middle Initial: ","Last Name: ", "SSN: ",
                               "Birth Date: ", "Address: ", "Sex: ", "Salary: ",
                               "Supervisor SSN: ", "Department Number: "};

        ArrayList<String> empInfoList = part4.getEmployeeInfo(ssn);
        int rowCount = 2;
        for(int i = 0; i < 10; i++) {

            Label label = new Label(infoLabels[i]);
            grid.add(label, 1, rowCount);

            Label data = new Label(empInfoList.get(i));
            grid.add(data, 2, rowCount);
            rowCount++;
        }

        // section 2: project info
        ArrayList<String> projects = part4.getProjectsAndHours(ssn);
        if(projects != null) {

            // display header
            Label projectInfo = new Label("Project Information:");
            grid.add(projectInfo, 0, rowCount++);

            // populate fields
            Iterator<String> projItr = projects.iterator();
            while(projItr.hasNext()) {

                Label label = new Label(projItr.next() + ":");
                grid.add(label, 1, rowCount);

                Label data = new Label(projItr.next() + "h");
                grid.add(data, 2, rowCount++);
            }
        }

        int vOffset = 0;

        // section 3: dependents
        if(hasDependents) {
            ArrayList<String> deps = part4.getDependent(ssn);

            // display header
            Label depsInfo = new Label("Dependents:");
            grid.add(depsInfo, 0, rowCount++);

            // population fields
            Iterator<String> depsItr = deps.iterator();
            while(depsItr.hasNext()) {

                Label nLabel = new Label("Name: ");
                grid.add(nLabel, 1, rowCount);
                Label name = new Label(depsItr.next());
                grid.add(name, 2, rowCount++);

                Label sLabel = new Label("Sex: ");
                grid.add(sLabel, 1, rowCount);
                Label sex = new Label(depsItr.next());
                grid.add(sex, 2, rowCount++);

                Label bLabel = new Label("Birth Date: ");
                grid.add(bLabel, 1, rowCount);
                Label bdate = new Label(depsItr.next().substring(0, 11));
                grid.add(bdate, 2, rowCount++);

                Label rLabel = new Label("Relation: ");
                grid.add(rLabel, 1, rowCount);
                Label relation = new Label(depsItr.next());
                grid.add(relation, 2, rowCount++);

                Label hr = new Label("=========================");
                grid.add(hr, 1, rowCount++, 2, 1);

                vOffset += 150;
            }
        }

        // add close button
        Button btn = new Button("Close Report");
        HBox hbBtn = new HBox(10);
        hbBtn.setAlignment(Pos.BOTTOM_RIGHT);
        hbBtn.getChildren().add(btn);
        grid.add(hbBtn, 1, rowCount);

        // on click close
        btn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                System.out.println("\nGoodbye!");
                stage.close();
            }
        });

        stage.setScene(new Scene(grid, 400, 600 + vOffset));
        stage.show();
    }

    public void addDependents(Stage stage, String ssn) {

        // add layout
        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);                  // center on screen
        grid.setHgap(10);                               // manage spacing between rows
        grid.setVgap(10);                               //     and columns
        grid.setPadding(new Insets(25, 10, 25, 10));    // manage space around edges of grid pane

        // add text, labels, and text fields
        Text scenetitle = new Text("Add Dependents");
        scenetitle.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
        grid.add(scenetitle, 0, 0, 2, 1);

        // name box
        Label name = new Label("Name:");
        grid.add(name, 0, 1);
        TextField nameBox = new TextField();
        grid.add(nameBox, 1, 1);

        // Add sex drop down
        Label sex = new Label("Sex:");
        grid.add(sex, 0, 2);
        ChoiceBox sBox = new ChoiceBox();
        sBox.setItems(FXCollections.observableArrayList("M", "F"));
        grid.add(sBox, 1, 2);

        // Birth Date box and picker
        Label birthDate = new Label("Birth Date:");
        grid.add(birthDate, 0, 3);
        DatePicker bdatePicker = new DatePicker();
        String pattern = "dd-MMM-yy";
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);
        bdatePicker.setPromptText("Select from Calendar ->");
        grid.add(bdatePicker, 1, 3);

        // Relationship field
        Label relation = new Label("Relation:");
        grid.add(relation, 0, 4);
        TextField relationBox = new TextField();
        grid.add(relationBox, 1, 4);

        // add checkbox for dependents
        CheckBox depBox = new CheckBox();
        depBox.setText("Add Another Dependent");
        grid.add(depBox, 1, 5);

        // add Submit button
        Button btn = new Button("Submit");
        HBox hbBtn = new HBox(10);
        hbBtn.setAlignment(Pos.BOTTOM_RIGHT);
        hbBtn.getChildren().add(btn);
        grid.add(hbBtn, 1, 6);

        // action on click
        btn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                String name = nameBox.getText();
                String sex = sBox.getValue().toString();
                String bdate = null;

                if (bdatePicker.getValue() != null) {
                    bdate = bdatePicker.getValue().format(formatter).toUpperCase();
                    System.out.println(bdate);
                } else {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Date Format Error");
                    alert.setHeaderText("Error Formatting Date of Birth");
                    alert.setContentText("Please select a date chosen from the calendar. This" +
                            " can be done by clicking on the calendar icon on the right.");
                    alert.showAndWait();
                    return;
                }

                String relation = relationBox.getText();

                part4.addDependent(ssn, name, sex, bdate, relation);

                if (depBox.isSelected()) {
                    nameBox.clear();
                    relationBox.clear();
                } else {
                    printReport(stage, ssn, true);
                }
            }
        });

        // action on enter pressed
        btn.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                if (event.getCode().equals(KeyCode.ENTER)) {
                    String name = nameBox.getText();
                    String sex = sBox.getValue().toString();
                    String bdate = null;

                    if (bdatePicker.getValue() != null) {
                        bdate = bdatePicker.getValue().format(formatter).toUpperCase();
                        System.out.println(bdate);
                    } else {
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setTitle("Date Format Error");
                        alert.setHeaderText("Error Formatting Date of Birth");
                        alert.setContentText("Please select a date chosen from the calendar. This" +
                                " can be done by clicking on the calendar icon on the right.");
                        alert.showAndWait();
                        return;
                    }

                    String relation = relationBox.getText();

                    part4.addDependent(ssn, name, sex, bdate, relation);

                    if (depBox.isSelected()) {
                        nameBox.clear();
                        relationBox.clear();
                    } else {
                        printReport(stage, ssn, true);
                    }
                }
            }
        });

        stage.setScene(new Scene(grid, 400, 300));
        stage.show();
    }

    // function to consume input chars > 1 in Middle initial text box
    public EventHandler<KeyEvent> maxLength(final Integer i) {
        return new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                TextField tf = (TextField) event.getSource();
                if(tf.getText().length() >= i) {
                    event.consume();
                }
            }
        };
    }

}
