# Magnolia Notification

The notification module triggers notifications via cron jobs.

## Requirements
* Java 17
* Magnolia >= 6.4

## Setup

### Add Maven dependency:
```xml
<dependency>
    <groupId>com.merkle.oss.magnolia</groupId>
    <artifactId>magnolia-notification</artifactId>
    <version>0.0.2</version>
</dependency>
```

### Configuration
`/resources/<module>/decorations/magnolia-notification-trigger/config.yaml`

```yaml
emails: ["bjoern.eschle@merkle.com"]
licenseConfig:
  enabled: true
  subjectTemplate: "ProjectXY ${instance} magnolia license expiration in ${expiration}"
  bodyTemplate: "The license for ${instance} is expiring in ${expiration} \n ${license}"
  reminderInDays: [30, 10, 1]
```
