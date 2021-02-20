package main

import (
	"C"
	"database/sql"
	_ "github.com/go-sql-driver/mysql"
	"encoding/json"
)

//export checkLogin
func checkLogin(param1, param2 *C.char) *C.char {
	email := C.GoString((*C.char)(param1))
	password := C.GoString((*C.char)(param2))
	db, _ := sql.Open("mysql", "root:582965@tcp(localhost:3306)/objective_database_admin?charset=utf8")
	defer db.Close()
	err := db.Ping()
	if err != nil {
		return nil
	}
	var qu = "select * from admin where admin_email = '" + email + "' and password = '" + password + "'"
	rows, _ := db.Query(qu)
	var adminMap map[string]string
	adminMap = make(map[string]string)
	for rows.Next() {
		var _id,admin_name,admin_phone, admin_email, password, gender, avatar, explain, professional string
		var id int
		rows.Scan(&id, &_id, &admin_name, &admin_phone, &admin_email, &password, &gender, &avatar, &explain, &professional)
		adminMap["_id"] = _id
		adminMap["admin_name"] = admin_name
		adminMap["admin_phone"] = admin_phone
		adminMap["gender"] = gender
		adminMap["avatar"] = avatar
		adminMap["explain"] = explain
		adminMap["professional"] = professional
	}
	jsons, err := json.Marshal(adminMap)
	if err != nil {
		return nil
	}
	return C.CString(string(jsons))
}

func main() {

}