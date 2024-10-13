# banque-misr-task-management-system business cycle..
   
  - the application is a task management system:
  - 1- any user with "user-role" can create-update-delete his own task.
  - 2- every create, update, and delete operation recorded in "history" document with all related data.
  - 3- the service that get the history is designed for "admin-role" only.
  - 4- admin can edit any task, but can only delete his tasks.
  - 5- the service of getting the tasks list is for admin and regular user, but only admins can see the tasks for all users.
  - 6- when the task edited by different user [ not createdBy user ], the system send an email for the task' creator.
  - 7- There is a scheduling part, email [ task' creator ] when the task dueDate will come after 2 days.
  - 
  - You'll find a code for generating a tasks on [BanqueMisrChallengeTaskManagementServiceApplication.java], it will generate 30 task for admin, and 30 for regular user. [edit it as you need by "regular user", and "admin user"]
  - ** Run the system and test as you like **