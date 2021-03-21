package controller;

import java.util.concurrent.Semaphore;

/*
 * 4 pessoas caminham, cada uma em um corredor diferente. Os 4 corredores terminam 
 * em uma única porta. Apenas 1 pessoa pode cruzar a porta, por vez. Considere que cada 
 * corredor tem 200m. e cada pessoa anda de 4 a 6 m/s. Cada pessoa leva de 1 a 2 segundos 
 * para abrir e cruzar a porta. Faça uma aplicação em java que simule essa situação.
 */

public class ThreadPessoa extends Thread
{
	private int percurso;
	private final int distancia = 200;
	private static int chegada[] = new int [4];
	private static int posicao;
	private int idPessoa;
	private Semaphore semaforo;
	
	public ThreadPessoa(int idPessoa, Semaphore semaforo)
	{
		this.idPessoa = idPessoa;
		this.semaforo = semaforo;
	}
	
	@Override
	public void run()
	{
		Correr();
		
		try
		{
			semaforo.acquire();
			CruzarPorta();
		}
		catch (InterruptedException e)
		{
			e.printStackTrace();
		}
		finally
		{
			semaforo.release();
		}
		
		if(posicao == 4)
		{
			System.out.println("-----------------------------\n          Podium:");
			
			for(int i = 0; i < chegada.length; i++)
			{				
				System.out.println("Corredor #" + chegada[i] + " foi o " + (i+1) + "º a chegar");
			}
			System.out.println("-----------------------------");
		}
	}

	private void Correr()
	{
		
		while(percurso < distancia)
		{
			int passo = (int) ((Math.random() * 2) + 4);
			percurso += passo;
			if ((distancia - percurso) > 0)
			{
				System.out.println("Corredor #" + idPessoa + " andou " + passo + "m, faltam: " + (distancia - percurso) + "m.");				
			}
			else
			{				
				System.out.println("Corredor #" + idPessoa + " andou " + passo + "m, faltam: 0m.");				
			}
			try
			{
				sleep(1000);
			}
			catch (InterruptedException e)
			{
				e.printStackTrace();
			}
		}
	}

	private void CruzarPorta()
	{
		System.out.println("Corredor #" + idPessoa + " chegou a porta.");
		int tempo = (int) ((Math.random() * 1001) + 1000);
		try
		{
			sleep(tempo);
		}
		catch (InterruptedException e)
		{
			e.printStackTrace();
		}
		System.out.println("Corredor #" + idPessoa + " levou " + (tempo / Math.pow(10, 3)) +"s para atravessar a porta.");
		chegada[posicao] = idPessoa;
		posicao ++;
	}
}
