package com.gugawag.so.ipc;

/**
 * Time-of-day server listening to port 6013.
 *
 * Figure 3.21
 *
 * @author Silberschatz, Gagne, and Galvin. Pequenas alterações feitas por Gustavo Wagner (gugawag@gmail.com)
 * Operating System Concepts  - Ninth Edition
 * Copyright John Wiley & Sons - 2013.
 */
import java.net.*;
import java.io.*;
import java.util.Date;

public class DateServer {
	public static void main(String[] args) {
		try {
			ServerSocket sock = new ServerSocket(6013);

			System.out.println("=== Servidor iniciado ===\n");
			System.out.println("Samuel de Morais Lima\n");
			System.out.println("Samuel de Morais Lima\n");

			while (true) {
				Socket client = sock.accept();
				System.out.println("Servidor recebeu comunicação do ip: " + client.getInetAddress() + "-" + client.getPort());

				// Criando uma nova thread conectar o cliente
				Thread clientThread = new Thread(() -> {
					try {
						PrintWriter pout = new PrintWriter(client.getOutputStream(), true);
						BufferedReader bin = new BufferedReader(new InputStreamReader(client.getInputStream()));

						// Escreve a data atual no socket
						pout.println(new Date().toString() + "-Boa noite alunos!");

						String line;
						while ((line = bin.readLine()) != null) {
							System.out.println("O cliente me disse:" + line);
						}
						client.close();
					} catch (IOException e) {
						System.err.println(e);
					}
				});

				clientThread.start();
			}
		} catch (IOException ioe) {
			System.err.println(ioe);
		}
	}
}
