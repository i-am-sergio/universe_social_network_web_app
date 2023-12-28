import time
import unittest
from selenium import webdriver
from selenium.webdriver.common.by import By
from selenium.webdriver.support.ui import WebDriverWait
from selenium.webdriver.support import expected_conditions as EC
from HtmlTestRunner import HTMLTestRunner

EXPECTED = "Expected: "
RESULT = "|Result: "
USERNAME = "user1@gmail.com"

class TestFollow(unittest.TestCase):
    def setUp(self):
        self.driver = webdriver.Chrome()
        self.driver.get("http://localhost:3000/")
        self.driver.delete_all_cookies()
        self.driver.execute_script("window.localStorage.clear()")
        self.xpath_username = """//*[@id="root"]/div/div[3]/div[2]/form/div[1]/input"""
        self.xpath_password = """//*[@id="root"]/div/div[3]/div[2]/form/div[2]/input"""
        self.xpath_btn_login = """//*[@id="root"]/div/div[3]/div[2]/form/div[3]/button"""
        self.xpath_section_followers = """//*[@id="root"]/div/div[3]/div[1]/div[3]/div[1]"""
        self.xpath_btn_follow = """//*[@id="root"]/div/div[3]/div[1]/div[3]/div[1]/button"""
        
    def tearDown(self):
        time.sleep(3)
        self.driver.close()
        
    def test_follow_success(self):
        self.driver.find_element(By.XPATH, self.xpath_username).send_keys(USERNAME)
        self.driver.find_element(By.XPATH, self.xpath_password).send_keys("admin")
        self.driver.find_element(By.XPATH, self.xpath_btn_login).click()
        
        wait_element = WebDriverWait(self.driver, 1200)
        wait_element.until(EC.visibility_of_element_located((By.XPATH,self.xpath_section_followers)))
        self.driver.find_element(By.XPATH, self.xpath_btn_follow).click()        
        text_btn_follow = self.driver.find_element(By.XPATH, self.xpath_btn_follow).text
        print(RESULT, text_btn_follow)
        self.assertEqual(text_btn_follow, "Unfollow")
        
    def test_unfollow_success(self):
        self.driver.find_element(By.XPATH, self.xpath_username).send_keys(USERNAME)
        self.driver.find_element(By.XPATH, self.xpath_password).send_keys("admin")
        self.driver.find_element(By.XPATH, self.xpath_btn_login).click()
        
        wait_element = WebDriverWait(self.driver, 1200)
        wait_element.until(EC.visibility_of_element_located((By.XPATH,self.xpath_section_followers)))
        self.driver.find_element(By.XPATH, self.xpath_btn_follow).click()
        text_btn_follow = self.driver.find_element(By.XPATH, self.xpath_btn_follow).text
        print(RESULT, text_btn_follow)
        self.assertEqual(text_btn_follow, "Follow")

if __name__ == "__main__":
    report_name = "follow_test_report.txt"
    suite = unittest.TestSuite()
    suite.addTest(TestFollow("test_follow_success"))
    suite.addTest(TestFollow("test_unfollow_success"))
    with open(report_name, 'w') as f:
        runner = HTMLTestRunner(stream=f, verbosity=2, report_name="reporte_de_pruebas")
        result = runner.run(suite)
    print(result)