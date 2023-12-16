import unittest
from selenium import webdriver
from selenium.webdriver.common.by import By
from HtmlTestRunner import HTMLTestRunner
from selenium.webdriver.support import expected_conditions as EC
from selenium.webdriver.support.ui import WebDriverWait

# FALTA AGREGAR LOS TESTS PARA QUE CUANDO SE LOGEE CORRECTAMENTE 
# CIERRE SESION Y SE LOGEE CON OTRO USUARIO
class TestLogin(unittest.TestCase):

    @classmethod
    def setUpClass(cls):
        cls.driver = webdriver.Chrome()

    @classmethod
    def tearDownClass(cls):
        # cls.driver.quit()
        pass

    def setUp(self):
        self.driver.get("http://localhost:3000/")
        self.xpath_username = """//*[@id="root"]/div/div[3]/div[2]/form/div[1]/input"""
        self.xpath_password = """//*[@id="root"]/div/div[3]/div[2]/form/div[2]/input"""
        self.xpath_btn_login = """//*[@id="root"]/div/div[3]/div[2]/form/div[3]/button"""
        self.xpath_login_error = """//*[@id="root"]/div/div[3]/div[2]/form/h3"""
        self.xpath_homename = """//*[@id="root"]/div/div[3]/div[1]/div[2]/div[2]/span[1]"""
        self.xpath_miperfil = """//*[@id="root"]/div/div[3]/div[1]/div[2]/span/a"""
        self.xpath_logout = """//*[@id="root"]/div/div[3]/div[1]/div[2]/button"""

    def test_login_success(self):
        self.driver.find_element(By.XPATH, self.xpath_username).send_keys("user1@gmail.com")
        self.driver.find_element(By.XPATH, self.xpath_password).send_keys("admin")

        self.driver.find_element(By.XPATH, self.xpath_btn_login).click()
        self.driver.implicitly_wait(5)
        
        result = self.driver.find_element(By.XPATH, self.xpath_homename).text
        print("Expected => {}".format("Shinji Ikari"))
        print("\tResult => {}".format(result))
        self.assertEqual(result, "Shinji Ikari")

    def test_login_invalid_password(self):
        self.driver.find_element(By.XPATH, self.xpath_username).send_keys("user1@gmail.com")
        self.driver.find_element(By.XPATH, self.xpath_password).send_keys("zxcvb")
        self.driver.find_element(By.XPATH,self.xpath_btn_login).click()
        self.driver.implicitly_wait(5)

        result = self.driver.find_element(By.XPATH, self.xpath_login_error).text

        print("Expected => {}".format("Login"))  
        print("\tResult => {}".format(result))
        self.assertEqual(result, "Login")

def custom_sorter(test_name):
    order = {
        'test_login_success': 1,
        'test_login_invalid_password': 2,
    }
    return order.get(test_name, 0)


if __name__ == "__main__":
    report_name = 'reporte_de_pruebas.txt'
    suite = unittest.TestSuite()
    suite.addTest(TestLogin('test_login_invalid_password'))
    suite.addTest(TestLogin('test_login_success'))

    with open(report_name, 'w') as f:
        runner = HTMLTestRunner(stream=f, verbosity=2, report_name="reporte_de_pruebas")
        result = runner.run(suite)
    print(result)