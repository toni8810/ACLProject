--creating a distributor with id 5
INSERT INTO `distributor` VALUES (5);
--creating a farmer under distributor 5 and with id 6
INSERT INTO `farmer` VALUES (6,5);
--creating a farmer under distributor 5 and with id 7
INSERT INTO `farmer` VALUES (7,5);
--creating a farm under farmer 6 and with id 15
INSERT INTO `farm` VALUES (15, 'Janes Farm', 6);
--creating a farm under farmer 7 and with id 16
INSERT INTO `farm` VALUES (16, 'Mikes Farm', 7);
--inserting class names that we want to secure
INSERT INTO `acl_class` VALUES (1,'acl.test.domain.Distributor'),(2,'acl.test.domain.Farm'),(3,'acl.test.domain.Farmer');
--inserting all the users
INSERT INTO `acl_sid` VALUES (1,1,'james'),(2,1,'jane'),(3,1,'john'),(4,1,'mike');
--inserting our distributor object (id, object_id_class, object_id_identity, parent_object,owner_sid, entries_inheriting)
INSERT INTO `acl_object_identity` VALUES (1,1,5,NULL,1,FALSE);
--inserting our farmer object (id, object_id_class, object_id_identity, parent_object,owner_sid,entries_inheriting) which will be owned by jane
INSERT INTO `acl_object_identity` VALUES (2,3,6,1,2,TRUE);
--inserting our farmer object (id, object_id_class, object_id_identity, parent_object,owner_sid,entries_inheriting) which will be owned by mike
INSERT INTO `acl_object_identity` VALUES (3,3,7,1,4,TRUE);
--inserting our farm object (id, object_id_class, object_id_identity, parent_object,owner_sid,entries_inheriting) which will be owned by jane
INSERT INTO `acl_object_identity` VALUES (4,2,15,2,2,TRUE);
--inserting our farm object (id, object_id_class, object_id_identity, parent_object,owner_sid,entries_inheriting) which will be owned by mike
INSERT INTO `acl_object_identity` VALUES (5,2,16,3,4,TRUE);
--grant james admin access to distributor 5 (id,acl_object_identity,ace_order,sid,mask, granting, audit_success, audit_failure)
INSERT INTO `acl_entry` VALUES (25,1,0,1,16,TRUE ,TRUE,TRUE);
--grant jane admin access to farmer 6 (id,acl_object_identity,ace_order,sid,mask, granting, audit_success, audit_failure)
INSERT INTO `acl_entry` VALUES (26,2,0,2,16,TRUE ,TRUE,TRUE);
--grant mike admin access to farmer 7 (id,acl_object_identity,ace_order,sid,mask, granting, audit_success, audit_failure)
INSERT INTO `acl_entry` VALUES (27,3,0,4,16,TRUE ,TRUE,TRUE);