# staj
<h1>KTMU Internship</h1>
<h2>Install guide:</h2>
<h3>Linux</h3>
<h4>Requirements:</h4>
<ul>
    <li>git</li>
    <li>maven</li>
    <li>mysql or mariadb</li>
    <li>jdk:11</li>
</ul>
<h4>Steps:</h4>
<ol>
    <li>Clone project: <code>git clone https://github.com/bekhub/staj.git</code></li>
    <li>go to staj directory: <code>cd staj-crm</code></li>
    <li>create database with name <code>staj</code></li>
    <li>open application.properties in src/main/resources and change username and password to yours</li>
    <li>run: <code>mvn com.github.eirslett:frontend-maven-plugin:1.7.6:install-node-and-npm -DnodeVersion="v12.16.3"</code></li>
    <li>then: <code>./mvnw spring-boot:run</code></li>
</ol>
