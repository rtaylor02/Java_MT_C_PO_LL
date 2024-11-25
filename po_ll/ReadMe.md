# Introduction
All about performance optimisation and low latency in Java.

# JMeter
Use JMeter to measure your application throughput. You do this by simulating (traffic) load of your application as per LSA (? ? Agreement).


## Steps
1. Create Thread Group that will create JMeter threads to send requeasts to our HttpServer. In the sample below, we create 200 users that will send 200 requests at the same time to our HttpServer
   ![image](https://github.com/user-attachments/assets/605f1f4f-d805-435d-b93a-2e7101749a51)
2. If you have inputs that each users can use as their parameter in their request, you can achieve this by a while loop (with the condition as shown) and input parameters (represented as csv):  
   ![while_loop](https://github.com/user-attachments/assets/e6f3b236-bc5e-4d12-a205-7aba25b98531)  
   (While loop)  
   ![Condition on while loop](https://github.com/user-attachments/assets/6cf5c46a-9bf6-494f-92ec-8a637b5cf02a)  
   (Condition on while loop)  
3. Add input parameters represented as csv from a file that will be in the each request:
   ![image](https://github.com/user-attachments/assets/5bd62d40-07a8-4f1b-a194-825827dea2ea)  
   (config_element as csv to simulate users' input parameters)  
   ![image](https://github.com/user-attachments/assets/1b2d4611-5591-4d6f-992c-4caf924388fd)  
   (To set: file path, variable names, recycle on EOF, stop thread on EOF (if it reaches end of file, stop thread))  
4. Add HTTP request in the loop with the relevant setup shown:  
   ![image](https://github.com/user-attachments/assets/fe9fe8af-42a8-400e-899b-0ad82c019170)  
   ![image](https://github.com/user-attachments/assets/92ba9a86-1dc4-44d8-a229-315187e9639d)  
5. Add summary report that will give us the througput  
   ![image](https://github.com/user-attachments/assets/5531517b-f0cc-4e99-8a25-df5f6fe9505b)  
   Throughput with 1 thread:  
   ![image](https://github.com/user-attachments/assets/2c25e927-35ca-4524-9219-7808c8ba55bc)  
   Throughput with 2 threads:  
   ![image](https://github.com/user-attachments/assets/06c401ff-a753-4290-80ed-cb7066630512)  
7. Add view result tree for inspection of each request-response:  
   ![image](https://github.com/user-attachments/assets/1c55bd3e-34e9-42f0-82e5-8e7974db0b82)  
   Sample details of request-response:  
   ![image](https://github.com/user-attachments/assets/2de7c054-947c-47cb-9d5a-fd8dca6a0bc1)

From the metrics, we can see that at roughly the same number of cores in the machine the performance plateau:  
![image](https://github.com/user-attachments/assets/6aa562a6-de60-4e09-b0b9-cf71372d0789)  

Sample code for steps above can be seen here.


 
  





# Java Flight Recorder (JFR) and Java Mission Control (JMC)

# Java Microbenchmark Harness (JMH)


# List of References
