# turn-login-server
Turn credential server written in Springboot

- CarrierInfo has password, ttl and uris properties.
- A client connects to this rest server with its carrier name and password and get its turn login.
- Otherside attackers are usually blocked becuase the carrier login is not known.

It can be run in the curl command as an user.
<pre>
curl -w "\n" --user www.example.com:examplepass http://localhost:8080/turn-login?username=test
</pre>