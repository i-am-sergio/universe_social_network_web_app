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
USERNAME2 = "user2@gmail.com"
HOLA_1 = "Hola 1"

class TestChat(unittest.TestCase):
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
        self.xpath_button_chat = """//*[@id="root"]/div/div[3]/div[3]/div[1]/a[2]"""
        self.xpath_section_chat_users = """//*[@id="root"]/div/div[3]/div[1]/div[2]/div"""
        self.xpath_chat_user_button = """//*[@id="root"]/div/div[3]/div[1]/div[2]/div/button[1]"""
        self.xpath_chat_text = """//*[@id="root"]/div/div[3]/div[2]/div[2]/div/div[3]/div[2]/div[1]/div/div[2]"""
        self.xpath_chat_send = """//*[@id="root"]/div/div[3]/div[2]/div[2]/div/div[3]/div[3]/button"""
        self.xpath_chat_message = """//*[@id="root"]/div/div[3]/div[2]/div[2]/div/div[2]/div/span[1]"""
        self.xpath_chat_message_1 = """//*[@id="root"]/div/div[3]/div[2]/div[2]/div/div[2]/div[1]/span[1]"""
        self.xpath_chat_message_2 = """//*[@id="root"]/div/div[3]/div[2]/div[2]/div/div[2]/div[2]/span[1]"""
        
    def tearDown(self):
        time.sleep(3)
        self.driver.close()
        
    def test_message_success(self):
        self.driver.find_element(By.XPATH, self.xpath_username).send_keys(USERNAME)
        self.driver.find_element(By.XPATH, self.xpath_password).send_keys("admin")
        self.driver.find_element(By.XPATH, self.xpath_btn_login).click()
        
        wait_element = WebDriverWait(self.driver, 1200)
        wait_element.until(EC.visibility_of_element_located((By.XPATH,self.xpath_section_followers)))
        self.driver.find_element(By.XPATH, self.xpath_btn_follow).click()
        time.sleep(10)
        text_btn_follow = self.driver.find_element(By.XPATH, self.xpath_btn_follow).text
        self.driver.find_element(By.XPATH, self.xpath_button_chat).click()
        
        
        
        wait_element.until(EC.visibility_of_element_located((By.XPATH,self.xpath_section_chat_users)))
        self.driver.find_element(By.XPATH, self.xpath_chat_user_button).click()
        
        wait_element.until(EC.visibility_of_element_located((By.XPATH,self.xpath_chat_message_1)))
        text_chat_message_1 = self.driver.find_element(By.XPATH, self.xpath_chat_message_1).text
        
        self.driver.find_element(By.XPATH, self.xpath_chat_text).send_keys("Hola 2")
        self.driver.find_element(By.XPATH, self.xpath_chat_send).click()
        
        wait_element.until(EC.visibility_of_element_located((By.XPATH,self.xpath_chat_message_2)))
        text_chat_message_2 = self.driver.find_element(By.XPATH, self.xpath_chat_message_2).text
        
        self.assertEqual(text_btn_follow, "Unfollow")
        self.assertEqual(text_chat_message_1, HOLA_1)
        self.assertEqual(text_chat_message_2, "Hola 2")
        
    def test_message_other_success(self):
        self.driver.find_element(By.XPATH, self.xpath_username).send_keys(USERNAME2)
        self.driver.find_element(By.XPATH, self.xpath_password).send_keys("admin")
        self.driver.find_element(By.XPATH, self.xpath_btn_login).click()
        
        wait_element = WebDriverWait(self.driver, 1200)
        wait_element.until(EC.visibility_of_element_located((By.XPATH,self.xpath_section_followers)))
        self.driver.find_element(By.XPATH, self.xpath_btn_follow).click()
        time.sleep(10) 
        text_btn_follow = self.driver.find_element(By.XPATH, self.xpath_btn_follow).text
        self.driver.find_element(By.XPATH, self.xpath_button_chat).click()
        
        wait_element.until(EC.visibility_of_element_located((By.XPATH,self.xpath_section_chat_users)))
        self.driver.find_element(By.XPATH, self.xpath_chat_user_button).click()
        self.driver.find_element(By.XPATH, self.xpath_chat_text).send_keys(HOLA_1)
        self.driver.find_element(By.XPATH, self.xpath_chat_send).click()
        
        wait_element.until(EC.visibility_of_element_located((By.XPATH,self.xpath_chat_message)))
        text_chat_message = self.driver.find_element(By.XPATH, self.xpath_chat_message).text
        
        self.assertEqual(text_btn_follow, "Unfollow")
        self.assertEqual(text_chat_message, HOLA_1)
        
if __name__ == "__main__":
    report_name = "chat_test_report.txt"
    suite = unittest.TestSuite()
    suite.addTest(TestChat("test_message_success"))
    suite.addTest(TestChat("test_message_other_success"))
    with open(report_name, 'w') as f:
        runner = HTMLTestRunner(stream=f, verbosity=2, report_name="reporte_de_pruebas")
        result = runner.run(suite)
    print(result)