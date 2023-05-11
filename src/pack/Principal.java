package pack;

import java.text.DecimalFormat;
import java.util.*;

public class Principal {

	static final int numarJucatori = 10;
	static List<List<Coordonate>> coordonateList = new ArrayList<>();
	static List<Double> finalDistances = new ArrayList<>();
	static Coordonate ballPosition = new Coordonate(0, 0);
	static int indexElements = 0;

	public static void main(String[] args) throws InterruptedException {
		optiuni();
	}

	public static int menu() {
		System.out.println();
		System.out.println("1.Introduceti coordonatele de start ale jucatorilor, urmat de pozitia mingii:");
		System.out.println("2.Introduceti numarul de repetari de 10 secunde:");
		System.out.println("3.Afisare distante parcurse totale de jucatori:");
		System.out.println("4.Afisare distante parcurse totale de echipe:");
		System.out.println("0.Terminare program");
		return citIntreg("Alege o optiune:");
	}

	public static void optiuni() throws InterruptedException {
		Scanner cit = new Scanner(System.in);
		int opt = menu();
		while (opt != 0) {
			switch (opt) {
			case 1:
				runAdder(1);
				break;
			case 2:
				System.out.println("Introduceti numarul de repetari: ");
				int repetari = cit.nextInt();
				runAdder(repetari);
				break;
			case 3:
				distanceCalculator();
				showDistances();
				break;
			case 4:
				showTotalDistances();
				break;
			default:
				System.out.println("Ai gresit optiunea, mai incearca");
			}
			opt = menu();
		}
		System.out.println("Program terminat");
	}

	public static int citIntreg(String citire) {
		System.out.print(citire);
		try {
			Scanner s = new Scanner(System.in);
			int I = s.nextInt();
			return I;
		} catch (Exception e) {
			System.out.println("Ai gresit optiunea.");
			citIntreg(citire);
		}
		return 0;
	}

	public static void runAdder(int numarExecutari) throws InterruptedException {
		for (int counter = 0; counter < numarExecutari; counter++) {
			List<Coordonate> newCoordinates = new ArrayList<>();
			for (int i = 0; i < numarJucatori; i++) {
				Coordonate coord = coordinateReader(i);
				newCoordinates.add(i, coord);
			}
			coordonateList.add(newCoordinates);
			Coordonate ballPosition = ballReader();
			posesionOfBall(coordonateList, ballPosition, indexElements);
			indexElements++;
			if (numarExecutari != 1) {
				Thread.sleep(10000);
			}
		}
	}

	public static void printAllCoordinates(List<List<Coordonate>> allCoordinates) {
		for (List<Coordonate> coordonateList : allCoordinates) {
			System.out.println("Lista noua:");
			for (Coordonate coordonate : coordonateList) {
				System.out.println(coordonate.getX() + "," + coordonate.getY());
			}
		}
	}

	public static void distanceCalculator() {
		for (int j = 0; j < coordonateList.get(0).size(); j++) {
			double distance = 0;
			for (int i = 0; i < coordonateList.size(); i++) {
				Coordonate currentCoordinate = coordonateList.get(i).get(j);
				if (i + 1 < coordonateList.size()) {
					Coordonate nextCoordinate = coordonateList.get(i + 1).get(j);
					distance += Math.sqrt((nextCoordinate.getX() - currentCoordinate.getX())
							* (nextCoordinate.getX() - currentCoordinate.getX())
							+ (nextCoordinate.getY() - currentCoordinate.getY())
									* (nextCoordinate.getY() - currentCoordinate.getY()));
				}
			}
			finalDistances.add(distance);
		}
	}

	public static void showDistances() {
		DecimalFormat df = new DecimalFormat("#.00");
		System.out.println("Distantele parcurse de jucatori sunt:");
		for (double distance : finalDistances) {
			System.out.println(df.format(distance));
		}
	}

	public static void showTotalDistances() {
		DecimalFormat df = new DecimalFormat("#.00");
		int counter = 0;
		double totalDistanceT1 = 0D;
		double totalDistanceT2 = 0D;
		for (double distance : finalDistances) {
			if (counter < 5) {
				totalDistanceT1 += distance;
			} else {
				totalDistanceT2 += distance;
			}
			counter++;
		}
		System.out.println("Distanta totala parcursa de echipa 1 este :");
		System.out.println(df.format(totalDistanceT1));
		System.out.println("Distanta totala parcursa de echipa 2 este :");
		System.out.println(df.format(totalDistanceT2));
	}

	public static Coordonate coordinateReader(int counter) {
		Scanner scanner = new Scanner(System.in);

		System.out.println("Introduceti coordonata x pentru jucatorul " + counter + " : ");
		int x = scanner.nextInt();
		while (x < 0 || x > 90) {
			System.out.println("Coordonata gresita. Valoarea trebuie sa fie intre 0 si 90: ");
			x = scanner.nextInt();
		}

		System.out.println("Introduceti coordonata y pentru jucatorul " + counter + " : ");
		int y = scanner.nextInt();
		while (y < 0 || y > 70) {
			System.out.print("Coordonata gresita. Valoarea trebuie sa fie intre 0 si 70: ");
			y = scanner.nextInt();
		}

		return new Coordonate(x, y);
	}

	public static Coordonate ballReader() {
		Scanner scanner = new Scanner(System.in);

		System.out.println("Introduceti coordonata x pentru minge");
		int x = scanner.nextInt();
		while (x < 0 || x > 90) {
			System.out.println("Coordonata gresita. Valoarea trebuie sa fie intre 0 si 90: ");
			x = scanner.nextInt();
		}

		System.out.println("Introduceti coordonata y pentru minge");
		int y = scanner.nextInt();
		while (y < 0 || y > 70) {
			System.out.print("Coordonata gresita. Valoarea trebuie sa fie intre 0 si 70: ");
			y = scanner.nextInt();
		}

		return new Coordonate(x, y);
	}

	public static double distance(double x1, double y1, double x2, double y2) {
		double xDiff = x2 - x1;
		double yDiff = y2 - y1;
		return Math.sqrt(xDiff * xDiff + yDiff * yDiff);
	}

	public static void posesionOfBall(List<List<Coordonate>> allCoordinates, Coordonate ballPosition, int index) {
		int count = 0;
		int[] Date = new int[4];
		Date[0] = 0;

		List<Coordonate> coordonateList = allCoordinates.get(index);
		for (Coordonate coord : coordonateList) {
			double result = Math.sqrt(Math.pow((coord.getX() - ballPosition.getX()), 2)
					+ Math.pow((coord.getY() - ballPosition.getY()), 2));

			count++;
			if (result < 10) {
				if (count <= 4) {
					Date[0]++;
					Date[1] = count; // Numarul jucatorului
					Date[2] = coord.getX(); // Coordonata X
					Date[3] = coord.getY(); // Coordonata Y
				} else {
					Date[0]++;
					Date[1] = count; // Numarul jucatorului
					Date[2] = coord.getX(); // Coordonata X
					Date[3] = coord.getY(); // Coordonata Y
				}
			}
		}

		if (Date[0] == 1) {
			if (Date[1] < 5) {
				System.out.println("Jucatorul din echipa 1 cu index: " + Date[1] + " coordonatele: " + Date[2] + ","
						+ Date[3] + " este in posesia mingii");
			} else {
				System.out.println("Jucatorul din echipa 2 cu index: " + Date[1] + " coordonatele: " + Date[2] + ","
						+ Date[3] + " este in posesia mingii");
			}
		} else {
			System.out.println("Nici un jucator nu este in posesia mingii");
		}
	}

}
