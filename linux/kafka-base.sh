#!/bin/sh
wget -O- https://apt.corretto.aws/corretto.key | sudo apt-key add - 
sudo add-apt-repository 'deb https://apt.corretto.aws stable main'
sudo apt-get update; sudo apt-get install -y java-11-amazon-corretto-jdk
wget https://archive.apache.org/dist/kafka/3.6.1/kafka_2.13-3.6.1.tgz
tar xzf kafka_2.13-3.6.1.tgz
rm kafka_2.13-3.6.1.tgz
mv kafka_2.13-3.6.1 /root