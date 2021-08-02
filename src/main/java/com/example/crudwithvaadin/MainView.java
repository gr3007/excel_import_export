package com.example.crudwithvaadin;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Anchor;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.IntegerField;
import com.vaadin.flow.component.textfield.NumberField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.StreamResource;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.vaadin.haijian.Exporter;
import com.vaadin.flow.component.html.H1;
import java.awt.*;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.sql.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

@Route
public class MainView extends VerticalLayout {
	private final CustomerRepository repo;
	public static String path;
	final Grid<Customer> grid1;
	final NumberField record1;
	final NumberField field1;
	final TextField s1;
	final IntegerField sheet1;
	final Grid<Customer> grid; //grid for data from excel
	final TextField filter;
	final NumberField record;
	final NumberField field;
	final TextField s;
	final IntegerField sheet;
	final Grid<ClientReference> grid2; //grid for data from database
	final Grid<Compare> grid3; //grid for transaction that matches
	final Grid<Compare> grid4; //grid for transaction that does not match

	private static final Logger log = LoggerFactory.getLogger(MainView.class);

	//create list of customer
	List<Customer> listOfCustomer = new ArrayList<>();

	//create list of client_reference
	List<ClientReference> clientReferenceList = new ArrayList<>();

	//create list to compare matched
	List<Compare> compareList = new ArrayList<>();

	private static String[] columnsM = {"id", "owner", "account_number", "x_vas_transaction_id", "value",
			"display_name", "product_recharge_type", "client_reference", "statusQprism", "created_at"};

	//list that do not match
	List<Compare> notMatchList = new ArrayList<>();

	private static String[] columnsNM = {"id", "owner", "account_number", "x_vas_transaction_id", "value",
			"display_name", "product_recharge_type", "client_reference", "statusExcel", "statusQprism", "created_at", "result"};

	public MainView(CustomerRepository repo) {
		this.repo = repo;
		this.grid1 = new Grid<>(Customer.class);
		this.record1 = new NumberField("Number of records");
		this.field1 = new NumberField("Number of fields");
		this.s1 = new TextField("Sheet name");
		this.sheet1 = new IntegerField();
		this.grid = new Grid<>(Customer.class);
		this.filter = new TextField();
		this.record = new NumberField("Number of records");
		this.field = new NumberField("Number of fields");
		this.s = new TextField("Sheet name");
		this.sheet = new IntegerField();
		this.grid2 = new Grid<>(ClientReference.class);
		this.grid3 = new Grid<>(Compare.class);
		this.grid4 = new Grid<>(Compare.class);

		//readFromRepo(); // read fata from repo

		readFromExcelList(); // read data from Excel list

		readFromQprism(); // read data from qprism

		// Button to output 2 grid
		Button compare = new Button("Compare status");
		compare.addClickListener(clickEvent ->

				compareStatus());
		add(compare);

}

	/*
	private void readFromRepo(){
		//TODO import excel from database
		H1 header = new H1("Import excel from database");

		//download grid as excel (xlsx)
		Anchor download1 = new Anchor(new StreamResource("my-excel.xlsx", Exporter.exportAsExcel(grid1)), "Download As Excel");

		//passing sheet number to get sheet name
		sheet1.setValue(0);
		sheet1.setHasControls(true);
		sheet1.setHelperText("Please specify sheet number");

		//configuring upload button
		Button upload1 = new Button("Upload excel");
		upload1.addClickListener(clickEvent -> {
			repo.deleteAll();
			try {
				//retrieving data from excel
				ExcelUtils excel1 = new ExcelUtils(getPath(), sheet1.getValue());

				//getting row count
				int row = excel1.getRowCount();
				record1.setValue((double) (row-1));
				record1.setReadOnly(true);

				//getting column count
				int column = excel1.getColumnCount();
				field1.setValue((double) column);
				field1.setReadOnly(true);

				//getting sheet name
				String sheet01 = excel1.getSheetName(sheet1.getValue());
				s1.setValue(sheet01);
				s1.setReadOnly(true);

				//iterating through excel file to get all values
				for (int i = 1; i < row; i++) {
					String id = excel1.getCellData(i, 0);
					String msisdn_id = excel1.getCellData(i, 1);
					String recipient_id = excel1.getCellData(i, 2);
					String owner = excel1.getCellData(i, 3);
					String account_number = excel1.getCellData(i, 4);
					String x_product_id = excel1.getCellData(i, 5);
					String x_product_list = excel1.getCellData(i, 6);
					String value = excel1.getCellData(i, 7);
					String display_name = excel1.getCellData(i, 8);
					String product_recharge_type = excel1.getCellData(i, 9);
					String client_reference = excel1.getCellData(i, 10);
					String x_vas_transaction_id = excel1.getCellData(i, 11);
					String status = excel1.getCellData(i, 12);
					String reversed = excel1.getCellData(i, 13);
					String created_at = excel1.getCellData(i, 14);
					String updated_at = excel1.getCellData(i, 15);

					//save to database
					repo.save(new Customer(id, msisdn_id, recipient_id, owner, account_number, x_product_id, x_product_list,
							value, display_name, product_recharge_type, client_reference, x_vas_transaction_id,
							status, reversed, created_at, updated_at));
				}

				//call method to populate grid
				//updateList1();

			} catch (Exception e) {
				e.printStackTrace();
			}
		});

		//adding different components in interface
		HorizontalLayout actions1 = new HorizontalLayout(upload1, sheet1);
		HorizontalLayout data1 = new HorizontalLayout(record1, field1, s1);
		add(header, grid1, data1, download1, actions1);

		//configuring grid height, columns, width
		grid1.setHeight("600px");
		grid1.setColumns("id", "msisdn_id", "recipient_id", "owner", "account_number", "x_product_id", "x_product_list",
				"value", "display_name", "product_recharge_type", "client_reference", "x_vas_transaction_id",
				"status", "created_at", "updated_at");
		grid1.getColumns().forEach(col -> col.setAutoWidth(true));
	}*/

	private void readFromExcelList(){

		//TODO import excel from a list
		H1 header1 = new H1("Import data from excel to grid");

		//download grid as excel (xlsx)
		Anchor download = new Anchor(new StreamResource("my-excel.xlsx", Exporter.exportAsExcel(grid)), "Download As Excel");

		//passing sheet number to get sheet name
		sheet.setValue(0);
		sheet.setHasControls(true);
		sheet.setHelperText("Please specify sheet number");

		//configuring upload button
		Button upload = new Button("Upload excel");
		upload.addClickListener(clickEvent -> {
			listOfCustomer.clear();
			try {
				//retrieving data from excel
				ExcelUtils excel = new ExcelUtils(getPath(), sheet.getValue());

				//getting row count
				int row = excel.getRowCount();
				record.setValue((double) (row-1));
				record.setReadOnly(true);

				//getting column count
				int column = excel.getColumnCount();
				field.setValue((double) column);
				field.setReadOnly(true);

				//getting sheet name
				String sheet0 = excel.getSheetName(sheet.getValue());
				s.setValue(sheet0);
				s.setReadOnly(true);

				//iterating through excel file to get all values
				for (int i = 1; i < row; i++) {
					String id = excel.getCellData(i, 0);
					String msisdn_id = excel.getCellData(i, 1);
					String recipient_id = excel.getCellData(i, 2);
					String owner = excel.getCellData(i, 3);
					String account_number = excel.getCellData(i, 4);
					String x_product_id = excel.getCellData(i, 5);
					String x_product_list = excel.getCellData(i, 6);
					String value = excel.getCellData(i, 7);
					String display_name = excel.getCellData(i, 8);
					String product_recharge_type = excel.getCellData(i, 9);
					String client_reference = excel.getCellData(i, 10);
					String x_vas_transaction_id = excel.getCellData(i, 11);
					String status = excel.getCellData(i, 12);
					String reversed = excel.getCellData(i, 13);
					String created_at = excel.getCellData(i, 14);
					String updated_at = excel.getCellData(i, 15);

					//add to list
					listOfCustomer.add(new Customer(id, msisdn_id, recipient_id, owner, account_number, x_product_id, x_product_list,
							value, display_name, product_recharge_type, client_reference, x_vas_transaction_id,
							status, reversed, created_at, updated_at));
				}

				//populate list to grid
				grid.setItems(listOfCustomer);

			} catch (Exception e) {
				e.printStackTrace();
			}
		});

		//adding different components in interface
		HorizontalLayout actions = new HorizontalLayout(upload, sheet);
		HorizontalLayout data = new HorizontalLayout(record, field, s);
		add(header1, grid, data, download, actions);

		//configuring grid height, columns, width
		grid.setHeight("600px");
		grid.setColumns("id", "msisdn_id", "recipient_id", "owner", "account_number", "x_product_id", "x_product_list",
				"value", "display_name", "product_recharge_type", "client_reference", "x_vas_transaction_id",
				"status", "created_at", "updated_at");
		grid.getColumns().forEach(col -> col.setAutoWidth(true));
	}

	private void readFromQprism(){
		//TODO import client_reference from qprism
		H1 header1 = new H1("Import data from qprism to grid");

		try {
			Connection con = DriverManager.getConnection(
					"jdbc:mysql://localhost:3306/excel","root", "root");
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * FROM excel");
			System.out.println("id  client_reference    status");
			while (rs.next()) {
				int id = rs.getInt("id");
				String client_reference = rs.getString("client_reference");
				String status = rs.getString("status");
				System.out.println(id+"   "+client_reference+"    "+status);
				clientReferenceList.add(new ClientReference(id, client_reference, status));
			}
		} catch(SQLException e) {
			System.out.println("SQL exception occured" + e);
		}

		grid2.setItems(clientReferenceList);
		grid2.setColumns("id", "client_reference", "status");
		grid2.getColumns().forEach(col -> col.setAutoWidth(true));
		add(header1, grid2);
	}

	private void compareStatus(){
		configureFilter();

		//download grid as excel (xlsx)
//		Anchor downloadM = new Anchor(new StreamResource("my-excel.xlsx", Exporter.exportAsExcel(grid3)), "Download As Excel");
//		Anchor downloadNM = new Anchor(new StreamResource("my-excel.xlsx", Exporter.exportAsExcel(grid4)), "Download As Excel");

		Button downloadMatch = new Button("Download list");
		downloadMatch.addClickListener(clickEvent -> {
			try {
				exportExcelTransactionMatch();
			} catch (IOException e) {
				e.printStackTrace();
			}
		});

		Button downloadNotMatch = new Button("Download list");
		downloadNotMatch.addClickListener(clickEvent -> {
			try {
				exportExcelTransactionNotMatch();
			} catch (IOException e) {
				e.printStackTrace();
			}
		});

		//TODO to compare list and qprism
		H1 h = new H1("List vs Qprism");
		for (int j=0; j<listOfCustomer.size(); j++){
			String id = listOfCustomer.get(j).getId();
			String owner = listOfCustomer.get(j).getOwner();
			String account_number = listOfCustomer.get(j).getAccount_number();
			String x_vas_transaction_id = listOfCustomer.get(j).getX_vas_transaction_id();
			String value = listOfCustomer.get(j).getValue();
			String display_name = listOfCustomer.get(j).getDisplay_name();
			String product_recharge_type = listOfCustomer.get(j).getProduct_recharge_type();
			String client_reference = listOfCustomer.get(j).getClient_reference();
			String statusExcel = listOfCustomer.get(j).getStatus();
			String statusQprism = clientReferenceList.get(j).getStatus();
			String created_at = listOfCustomer.get(j).getCreated_at();
			String result;
			if (statusExcel.toLowerCase().equals(statusQprism) || (statusExcel.equals("SUCCESS") && statusQprism.equals("complete"))){
				result = "Ok";
				compareList.add(new Compare(id, owner, account_number, x_vas_transaction_id, value,
						display_name, product_recharge_type, client_reference, statusQprism, created_at));
			}else {
				if((statusExcel.equals("FAIL") && statusQprism.equals("pending"))){
					result = "Transaction pending";
				}else {
					result = "Transaction does not match";
				}
				notMatchList.add(new Compare(id, owner, account_number, x_vas_transaction_id, value,
						display_name, product_recharge_type, client_reference, statusExcel, statusQprism, created_at, result));
			}
		}
		updateListM();
		grid3.setColumns("id", "owner", "account_number", "x_vas_transaction_id", "value",
				"display_name", "product_recharge_type", "client_reference", "statusQprism", "created_at");
		grid3.getColumns().forEach(col -> col.setAutoWidth(true));

		updateListNM();
		grid4.setColumns("id", "owner", "account_number", "x_vas_transaction_id", "value",
				"display_name", "product_recharge_type", "client_reference", "statusExcel", "statusQprism", "created_at", "result");
		grid4.getColumns().forEach(col -> col.setAutoWidth(true));

		add(h, grid3, downloadMatch);
		add(filter, grid4, downloadNotMatch);
	}

	//configuring filter field
	private void configureFilter() {
		filter.setPlaceholder("Filter by Result...");
		filter.setWidth("16em");
		filter.setClearButtonVisible(true);
		filter.setValueChangeMode(ValueChangeMode.LAZY);
		filter.addValueChangeListener(e -> updateListNM());
	}

	//find data according to filter
	private List<Compare> filterResultNM(String stringFilter){
		if (stringFilter == null || stringFilter.isEmpty()) {
			return notMatchList;
		} else {
			return notMatchList
					.stream()
					.filter(c -> c.getResult().contains(stringFilter))
					.collect(Collectors.toList());
		}
	}

	//populate match status to grid
	private void updateListM() {
		grid3.setItems(compareList);
	}

	//populate not match status to grid
	private void updateListNM(){
		grid4.setItems(filterResultNM(filter.getValue()));
	}

	//Getting path of excel
	public static String getPath(){
		//opening file explorer to choose excel file to upload
		FileDialog dialog = new FileDialog((Frame)null, "Select File to Open");
		dialog.setMode(FileDialog.LOAD);
		dialog.setVisible(true);
		String directory = dialog.getDirectory();
		String file = dialog.getFile();
		log.info(directory + file + " chosen.");
		return path = directory + file;
	}

	public void exportExcelTransactionMatch() throws IOException, FileNotFoundException {
		// Create a Workbook
		Workbook workbook = new XSSFWorkbook(); // new HSSFWorkbook() for generating `.xls` file

        /* CreationHelper helps us create instances of various things like DataFormat,
           Hyperlink, RichTextString etc, in a format (HSSF, XSSF) independent way */
		CreationHelper createHelper = workbook.getCreationHelper();

		// Create a Sheet
		Sheet sheet = workbook.createSheet("Transaction OK");

		// Create a Font for styling header cells
		Font headerFont = workbook.createFont();
		headerFont.setBold(true);

		// Create a CellStyle with the font
		CellStyle headerCellStyle = workbook.createCellStyle();
		headerCellStyle.setFont(headerFont);

		// Create a Row
		Row headerRow = sheet.createRow(0);

		// Create cells
		for(int i = 0; i < columnsM.length; i++) {
			Cell cell = headerRow.createCell(i);
			cell.setCellValue(columnsM[i]);
			cell.setCellStyle(headerCellStyle);
		}

		// Create Cell Style for formatting Date
		CellStyle dateCellStyle = workbook.createCellStyle();
		dateCellStyle.setDataFormat(createHelper.createDataFormat().getFormat("dd-MM-yyyy"));

		// Create Other rows and cells with employees data
		int rowNum = 1;
		for(Compare compare: compareList) {
			Row row = sheet.createRow(rowNum++);

			row.createCell(0)
					.setCellValue(compare.getId());
			row.createCell(1)
					.setCellValue(compare.getOwner());
			row.createCell(2)
					.setCellValue(compare.getAccount_number());
			row.createCell(3)
					.setCellValue(compare.getX_vas_transaction_id());
			row.createCell(4)
					.setCellValue(compare.getValue());
			row.createCell(5)
					.setCellValue(compare.getDisplay_name());
			row.createCell(6)
					.setCellValue(compare.getProduct_recharge_type());
			row.createCell(7)
					.setCellValue(compare.getClient_reference());
			row.createCell(8)
					.setCellValue(compare.getStatusQprism());
			row.createCell(9)
					.setCellValue(compare.getCreated_at());
		}

		// Resize all columns to fit the content size
		for(int i = 0; i < columnsM.length; i++) {
			sheet.autoSizeColumn(i);
		}

		// Get today's date and time
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd-MM-yyyy HH-mm-ss");
		LocalDateTime now = LocalDateTime.now();
		String date = dtf.format(now);

		// Write the output to a file
		FileOutputStream fileOut = new FileOutputStream("Transaction OK " + date + ".xlsx");
		workbook.write(fileOut);
		fileOut.close();

		// Closing the workbook
		workbook.close();
	}

	public void exportExcelTransactionNotMatch() throws IOException, FileNotFoundException {
		// Create a Workbook
		Workbook workbook = new XSSFWorkbook(); // new HSSFWorkbook() for generating `.xls` file

        /* CreationHelper helps us create instances of various things like DataFormat,
           Hyperlink, RichTextString etc, in a format (HSSF, XSSF) independent way */
		CreationHelper createHelper = workbook.getCreationHelper();

		// Create a Sheet
		Sheet sheet = workbook.createSheet("Transaction not OK");

		// Create a Font for styling header cells
		Font headerFont = workbook.createFont();
		headerFont.setBold(true);

		// Create a CellStyle with the font
		CellStyle headerCellStyle = workbook.createCellStyle();
		headerCellStyle.setFont(headerFont);

		// Create a Row
		Row headerRow = sheet.createRow(0);

		// Create cells
		for(int i = 0; i < columnsNM.length; i++) {
			Cell cell = headerRow.createCell(i);
			cell.setCellValue(columnsNM[i]);
			cell.setCellStyle(headerCellStyle);
		}

		// Create Cell Style for formatting Date
		CellStyle dateCellStyle = workbook.createCellStyle();
		dateCellStyle.setDataFormat(createHelper.createDataFormat().getFormat("dd-MM-yyyy"));

		// Create Other rows and cells with employees data
		int rowNum = 1;
		for(Compare compare: notMatchList) {
			Row row = sheet.createRow(rowNum++);

			row.createCell(0)
					.setCellValue(compare.getId());
			row.createCell(1)
					.setCellValue(compare.getOwner());
			row.createCell(2)
					.setCellValue(compare.getAccount_number());
			row.createCell(3)
					.setCellValue(compare.getX_vas_transaction_id());
			row.createCell(4)
					.setCellValue(compare.getValue());
			row.createCell(5)
					.setCellValue(compare.getDisplay_name());
			row.createCell(6)
					.setCellValue(compare.getProduct_recharge_type());
			row.createCell(7)
					.setCellValue(compare.getClient_reference());
			row.createCell(8)
					.setCellValue(compare.getStatusExcel());
			row.createCell(9)
					.setCellValue(compare.getStatusQprism());
			row.createCell(10)
					.setCellValue(compare.getCreated_at());
			row.createCell(11)
					.setCellValue(compare.getResult());
		}

		// Resize all columns to fit the content size
		for(int i = 0; i < columnsNM.length; i++) {
			sheet.autoSizeColumn(i);
		}

		// Get today's date and time
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd-MM-yyyy HH-mm-ss");
		LocalDateTime now = LocalDateTime.now();
		String date = dtf.format(now);

		// Write the output to a file
		FileOutputStream fileOut = new FileOutputStream("Transaction not OK " + date + ".xlsx");
		workbook.write(fileOut);
		fileOut.close();

		// Closing the workbook
		workbook.close();
	}
}
