package Main;

import java.util.Random;
import java.util.Scanner;
import java.util.Vector;

public class Main {
	Scanner sc = new Scanner(System.in);
	Random rand = new Random();
	Vector<Karyawan> karyawanVector = new Vector<>();
	public static int countManager=0, countSV=0, countAdmin=0;
	
	public Main() {
		mainMenu();

	}
	
	public void mainMenu() {
		int choice;
		
		do {
			System.out.println("Main Menu:");
			System.out.println("==========================");
			System.out.println("1. Insert Data Karyawan");
			System.out.println("2. View Data Karyawan");
			System.out.println("3. Update Data Karyawan");
			System.out.println("4. Delete Data Karyawan");
			System.out.println("5. Exit");
			System.out.println("==========================");
			System.out.println("Pilihan:");
			choice = validateNumber();
			
			switch (choice) {
			case 1:
				insert();
				break;
			case 2:
				view();
				break;
			case 3:
				update();
				break;
			case 4:
				delete();
				break;
			case 5:
				System.out.println("Terima kasih telah menggunakan aplikasi ini!");
				System.exit(0);
				break;
			}
		}while(choice!=5);
	}
	
	public void insert() {
		String name, gender, position, code;
		int wages;
		double bonus=0;
		
		do {
			System.out.println("Input nama karyawan [>= 3]: ");
			name = sc.nextLine();
		} while (name.length()<3);
		
		do {
			System.out.println("Input jenis kelamin [Laki-laki | Perempuan] (Case Sensitive): ");
			gender = sc.nextLine();
		} while (!(gender.equals("Laki-laki") || gender.equals("Perempuan")));
		
		do {
			System.out.println("Input jabatan [Manager | Supervisor | Admin] (Case Sensitive): ");
			position = sc.nextLine();
		} while (!(position.equals("Manager") || position.equals("Supervisor") || position.equals("Admin")));
		
		wages = wages(position);
		code = generateCode();
		
		Karyawan newKaryawan = new Karyawan(code, name, gender, position, wages);
		karyawanVector.add(newKaryawan);
		
		bonus = checkBonus(position);
		
		System.out.println("Berhasil menambahkan karyawan dengan id "+code);
		
		if(bonus!=0) {
			System.out.println("Bonus sebesar "+bonus+"% telah diberikan kepada karyawan dengan id:");
			for(int i=0; i<karyawanVector.size()-1; i++) {
				if(karyawanVector.get(i).position.equals(position)) {
					System.out.println(karyawanVector.get(i).code);
				}
			}
		}
		
		System.out.println("ENTER to return");
		
	}
	
	public void view() {
		if (karyawanVector.isEmpty()) {
			System.out.println("Data Karyawan masih kosong!");
			mainMenu();
		}else {
			sort();
			for (int i = 0; i < karyawanVector.size(); i++) {
				System.out.println("No : " + (i+1));
				System.out.println("Kode Karyawan : " + karyawanVector.get(i).code);
				System.out.println("Nama Karyawan : " + karyawanVector.get(i).name);
				System.out.println("Jenis Kelamin : " + karyawanVector.get(i).gender);
				System.out.println("Jabatan : " + karyawanVector.get(i).position);
				System.out.println("Gaji Karyawan : " + karyawanVector.get(i).wages);
				System.out.println("==========================");
			}
		}
	}
	
	public void update() {
		view();
		int idx, wages;
		String name, gender, position;
		do {
			System.out.println("Input nomor urutan karyawan yang ingin diupdate: ");
			idx = validateNumber();
		} while (idx < 1 || idx > karyawanVector.size());
		
		System.out.println("Input nama karyawan [>= 3]: ");
		name = sc.nextLine();
		
		do {
			System.out.println("Input jenis kelamin [Laki-laki | Perempuan] (Case Sensitive): ");
			gender = sc.nextLine();
		} while (!(gender.equals("Laki-laki") || gender.equals("Perempuan")||gender.equals("0")));
		
		do {
			System.out.println("Input jabatan [Manager | Supervisor | Admin] (Case Sensitive): ");
			position = sc.nextLine();
		} while (!(position.equals("Manager") || position.equals("Supervisor") || position.equals("Admin")||position.equals("0")));
		
		wages = wages(position);
		
		if(!name.equals("0")) {
			karyawanVector.get(idx-1).name = name;
		}
		if(!gender.equals("0")) {
			karyawanVector.get(idx-1).gender = gender;
		}
		if(!position.equals("0")) {
			karyawanVector.get(idx-1).position = position;
			karyawanVector.get(idx-1).wages = wages;
		}
		
		System.out.println("Berhasil mengupdate karyawan dengan id "+ karyawanVector.get(idx-1).code);
		System.out.println("ENTER to return");
	
	}
	
	
	public void delete() {
		view();
		int idx;
		String removedCode, removedPosition;
		do {
			System.out.println("Input nomor urutan karyawan yang ingin dihapus: ");
			idx = validateNumber();
		} while (idx < 1 || idx > karyawanVector.size());
		
		removedCode = karyawanVector.get(idx-1).code;
		removedPosition = karyawanVector.get(idx-1).position;
		
		karyawanVector.remove(idx-1);
		if(removedPosition.equals("Manager")) {
			countManager--;
		}else if(removedPosition.equals("Supervisor")) {
			countSV--;
		}else if(removedPosition.equals("Admin")) {
			countAdmin--;
		}
		
		System.out.println("Karyawan dengan kode " + removedCode + " berhasil dihapus");
		System.out.println("ENTER to return");
		
	}
	
	public void sort() {
		for (int i = 0; i < karyawanVector.size(); i++) {
			for (int j = 0; j <	karyawanVector.size()-1; j++) {
				
				String name1 = karyawanVector.get(j).name;
				String name2 = karyawanVector.get(j+1).name;
				
				if (name1.compareTo(name2) > 0) {
					Karyawan temp = karyawanVector.get(j);
					karyawanVector.set(j, karyawanVector.get(j+1));
					karyawanVector.set(j+1, temp);
				}
				
			}
		}
	}
	
	public int validateNumber() {
		int temp = 0;

		try {
			temp = sc.nextInt();
			sc.nextLine();
		} catch (Exception e) {
			System.out.println("Your input must be numeric!!");
			sc.nextLine();
		}

		return temp;
	}
	
	public int wages(String position) {
		int gaji = 0;
		switch (position) {
		case "Manager":
			gaji = 8000000;
			break;
			
		case "Supervisor":
			gaji = 6000000;
			break;
			
		case "Admin":
			gaji = 4000000;
			break;
		}
		return gaji;
	}
	
	public double checkBonus(String position) {
		double bonus = 0;
		
		switch (position) {
			case "Manager":
				countManager++;
				break;
			
			case "Supervisor":
				countSV++;
				break;
			
			case "Admin":
				countAdmin++;
				break;
		}
		int size = karyawanVector.size();
		if(countManager==7||countManager==4) {
			bonus=10;
			for(int j=0; j<size-1; j++) {
				if(karyawanVector.get(j).position.equals("Manager")) {
					karyawanVector.get(j).wages = karyawanVector.get(j).wages*110/100;
				}
			}
			
		}
		if(countSV==7||countSV==4) {
			bonus=7.5;
			for(int j=0; j<size-1; j++) {
				if(karyawanVector.get(j).position.equals("Supervisor")) {
					karyawanVector.get(j).wages = karyawanVector.get(j).wages*1075/1000;
				}
			}
			
		}
		if(countAdmin==7||countAdmin==4) {
			bonus=5;
			for(int j=0; j<size-1; j++) {
				if(karyawanVector.get(j).position.equals("Admin")) {
					karyawanVector.get(j).wages = karyawanVector.get(j).wages*105/100;
				}
			}
		}
			
		return bonus;
	
	}
	
	public String generateCode() {
		
		String code = String.format("%c%c-%d%d%d%d",(char)(rand.nextInt(26)+'A'), (char)(rand.nextInt(26)+'A'), rand.nextInt(10),rand.nextInt(10),rand.nextInt(10),rand.nextInt(10));
		
		
		return code;
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		new Main();
	}

}
