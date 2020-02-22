# turn-login-server
Turn credential server written in Springboot

- It needs to connect to redis.
- It has a rest interface for doing crud with carrier-info.
- CarrierInfo has password, ttl and uris properties.
- A client connects to this rest server with its carrier name and password and get its turn login.
- Otherside attackers are usually blocked becuase the carrier login is not known.
