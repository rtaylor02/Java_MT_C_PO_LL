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
   ![image](https://github.com/user-attachments/assets/5bd62d40-07a8-4f1b-a194-825827dea2ea)
   (config_element as csv to simulate users' input parameters)
   ![image](https://github.com/user-attachments/assets/1b2d4611-5591-4d6f-992c-4caf924388fd)
   (To set: file path, variable names, recycle on EOF, stop thread on EOF (if it reaches end of file, stop thread))
   
  





# Java Flight Recorder (JFR) and Java Mission Control (JMC)

# Java Microbenchmark Harness (JMH)


# List of References
