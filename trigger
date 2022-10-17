CREATE or REPLACE trigger Sh_Backup
BEFORE INSERT OR DELETE OR UPDATE ON superheroes
FOR EACH ROW
ENABLE 
BEGIN
  IF INSERTING THEN
    INSERT INTO superheroes_backup (SH_NAME) VALUES (:NEW.SH_NAME);  
  ELSIF DELETING THEN
    DELETE FROM superheroes_backup WHERE SH_NAME =:old.sh_name; 
  ELSIF UPDATING THEN
    UPDATE superheroes_backup 
    SET SH_NAME =:new.sh_name WHERE SH_NAME =:old.sh_name;
  END IF;
END;
/
