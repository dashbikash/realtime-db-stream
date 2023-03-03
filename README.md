# realtime-db-streameed 
Problem
1. Source is the MySql which is a streaming database on which data is changing regularly.
2. Need to provide realtime data to external services/clients througn,
  - API (Througn Mongodb) for application.
  - Cassandra DB for Analytics.
3. The above required migrations should occur without hitting the streaming database (Mysql).

  
